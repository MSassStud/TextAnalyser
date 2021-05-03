package hci.project.textanalyser.noun;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.IntStream;

import edu.stanford.nlp.coref.data.CorefChain.CorefMention;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NounExtractor {

    private final StanfordCoreNLP pipeline;
    
    public static NounExtractor forCaselessSentences() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,truecase,pos,lemma,ner,parse,coref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        return new NounExtractor(pipeline);
    }
    
    public static NounExtractor forCasedSentences() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        return new NounExtractor(pipeline);
    }
    
    private NounExtractor(StanfordCoreNLP pipeline) {
        this.pipeline = new StanfordCoreNLP();
    }
    
    public List<Noun> extract(String text) {
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        
        return extractNouns(document);
    }
    
    private List<Noun> extractNouns(CoreDocument document) {
        List<CoreLabel> simpleNouns = extractSimpleNouns(document);
        List<MentionedNoun> referencedNouns = extractReferencedNouns(document);
        
        return combine(
            mapSimpleNouns(simpleNouns),
            mapReferencedNouns(referencedNouns));
    }
    
    private List<CoreLabel> extractSimpleNouns(CoreDocument document) {
        return document.tokens().stream()
            .filter(token -> isNoun(token))
            .collect(toList());
    }
    
    private List<Noun> mapSimpleNouns(List<CoreLabel> nounTokens) {
        List<Noun> nouns = new ArrayList<>();
        for (int i = 0; i < nounTokens.size(); i++) {
            CoreLabel token = nounTokens.get(i);
            if (isLabeledPerson(token)) {
                int end = findPersonRangeEnd(nounTokens, i);
                nouns.add(createPersonName(nounTokens, i, end));
                i = end;
            } else {
                Noun noun = createSimpleNoun(token);
                nouns.add(noun);
            }
        }
        return nouns;
    }

    private Noun createSimpleNoun(CoreLabel token) {
        Noun.Location location = new Noun.Location(token.sentIndex(), token.index() - 1, token.index() - 1);
        return new Noun(location, List.of(token.word()), false);
    }

    private Noun createPersonName(List<CoreLabel> nounTokens, int startIndex, int endIndex) {
        CoreLabel start = nounTokens.get(startIndex);
        CoreLabel end = nounTokens.get(endIndex);
        
        Noun.Location location = new Noun.Location(start.sentIndex(), start.index() - 1, end.index() - 1);
        List<String> words = IntStream.rangeClosed(startIndex, endIndex)
            .mapToObj(nounTokens::get)
            .map(CoreLabel::word)
            .collect(toList());
        return new Noun(location, words, true);
    }
    
    private int findPersonRangeEnd(List<CoreLabel> tokens, int startIndex) {
        int end = startIndex;
        for (int j = startIndex; j < tokens.size() && tokens.get(j).sentIndex() == tokens.get(startIndex).sentIndex() && isLabeledPerson(tokens.get(j)); j++) {
            end = j;
        }
        return end;
    }
    
    private boolean isLabeledPerson(CoreLabel token) {
        return "PERSON".equals(token.ner());
    }
    
    private List<MentionedNoun> extractReferencedNouns(CoreDocument document) {
        List<MentionedNoun> mentionedNouns = new ArrayList<>();
        for (var chain : document.corefChains().values()) {
            for (var mention : chain.getMentionsInTextualOrder()) {
                CorefMention representative = chain.getRepresentativeMention();
                List<CoreLabel> tokens = representativeNouns(document, representative);
                if (!tokens.isEmpty()) {
                    MentionedNoun mentionedNoun = new MentionedNoun();
                    mentionedNoun.mention = mention;
                    mentionedNoun.nouns = tokens;
                    mentionedNouns.add(mentionedNoun);
                }
            }
        }
        
        return mentionedNouns;
    }
    
    private List<CoreLabel> representativeNouns(CoreDocument document, CorefMention mention) {
        CoreSentence sentence = document.sentences().get(mention.sentNum - 1);
        
        return IntStream.rangeClosed(mention.startIndex - 1, mention.endIndex -1)
            .mapToObj(i -> sentence.tokens().get(i))
            .filter(token -> isNoun(token))
            .collect(toList());
    }
    
    private boolean isNoun(CoreLabel token) {
        return Set.of("NN", "NNP", "NNS", "NNPS").contains(token.tag());
    }
    
    private List<Noun> mapReferencedNouns(List<MentionedNoun> mentions) {
        return mentions.stream()
            .map(m -> {
                Noun.Location location = new Noun.Location(m.mention.sentNum - 1, m.mention.startIndex - 1, m.mention.endIndex - 2);
                List<String> words = m.nouns.stream().map(CoreLabel::word).collect(toList());
                boolean person = m.nouns.stream().anyMatch(n -> "PERSON".equals(n.ner()));
                return new Noun(location, words, person);
            })
            .collect(toList());
    }
    
    private List<Noun> combine(List<Noun> a, List<Noun> b) {
        ArrayList<Noun> combined = new ArrayList<>();
        combined.addAll(a);
        combined.addAll(b);
        
        Comparator<Noun> textOrder = Comparator.comparing((Noun n) -> n.location().sentence())
            .thenComparing((Noun n) -> n.location().startToken())
            .thenComparing((Noun n) -> n.location().endToken());
        
        combined.sort(textOrder);
        
        int i = 0;
        while (i < combined.size() - 1) {
            Noun left = combined.get(i);
            Noun right = combined.get(i + 1);
            if (left.contains(right)) {
                combined.remove(i + 1);
            } else if (right.contains(left)) {
                combined.remove(i);
            } else {
                i++;
            }
        }
        
        return combined;
    }
    
    private static class MentionedNoun {
        CorefMention mention;
        List<CoreLabel> nouns;
    }
}
