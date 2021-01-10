package hci.project.textanalyser.noun;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import edu.stanford.nlp.coref.data.CorefChain.CorefMention;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NounExtractor {

    public static Map<Integer, List<String>> extractNouns(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        
        List<CoreSentence> sentences = document.sentences();
        Map<Integer, List<Noun>> sentenceNouns = extractNouns(sentences);
        
        extractReferencedNouns(document, sentences, sentenceNouns);
        
        return nouns(sentences, sentenceNouns);
    }

    private static Map<Integer, List<String>> nouns(List<CoreSentence> sentences, Map<Integer, List<Noun>> sentenceNouns) {
        Map<Integer, List<String>> nouns = new HashMap<>();
        for (int s = 0; s < sentences.size(); s++) {
            List<Noun> list = sentenceNouns.get(s + 1);
            list.sort(Comparator.comparing(Noun::getStart).thenComparing(Noun::getEnd));
            
            List<String> collect = list.stream().map(Noun::getText).collect(Collectors.toList());
            combineRepetitions(collect);
            nouns.put(s + 1, collect);
        }
        return nouns;
    }

    private static void extractReferencedNouns(CoreDocument document, List<CoreSentence> sentences,
            Map<Integer, List<Noun>> sentenceNouns) {
        for (var chain : document.corefChains().values()) {
            List<CorefMention> mentionsInTextualOrder = chain.getMentionsInTextualOrder();
            for (CorefMention mention : mentionsInTextualOrder) {
                List<String> words = new ArrayList<>();
                CorefMention representativeMention = chain.getRepresentativeMention();
                for (int i = representativeMention.startIndex - 1; i < representativeMention.endIndex - 1; i++) {
                    CoreLabel coreLabel = sentences.get(representativeMention.sentNum - 1).tokens().get(i);
                    if (hasNounTag(coreLabel)) {
                        words.add(coreLabel.word());
                    }
                }
                
                List<Noun> indirectNouns = new ArrayList<>();
                indirectNouns.add(new Noun(mention.startIndex, mention.endIndex-1, String.join(" ", words)));
                sentenceNouns.get(mention.sentNum).addAll(indirectNouns);
            }
        }
    }

    private static Map<Integer, List<Noun>> extractNouns(List<CoreSentence> sentences) {
        Map<Integer, List<Noun>> sentenceNouns = new HashMap<>();
        for (int s = 0; s < sentences.size(); s++) {
            var sentence = sentences.get(s);
            List<Noun> nouns = new ArrayList<>();
            for (var token : sentence.tokens()) {
                if (hasNounTag(token)) {
                    nouns.add(new Noun(token.index(), token.index(), token.word()));
                }
            }
            sentenceNouns.put(s+1, nouns);
        }
        return sentenceNouns;
    }

    private static void combineRepetitions(List<String> collect) {
        int i = 1;
        while (i < collect.size()) {
            if (collect.get(i - 1).equals(collect.get(i))) {
                collect.remove(i);
            } else {
                i++;
            }
        }
    }

    private static boolean hasNounTag(CoreLabel token) {
        return Set.of("NN", "NNP", "NNS", "NNPS").contains(token.tag());
    }
    
    public static class Noun {
        private final int start; // word index, starts at 1
        private final int end; // word index, starts at 1
        private final String text;

        public Noun(int start, int end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }

        public String getText() {
            return text;
        }
        
        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
        
        @Override
        public String toString() {
            return getStart() + "-" + getEnd() + ":" + text;
        }
    }
}
