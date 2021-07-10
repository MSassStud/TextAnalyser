package hci.project.textanalyser.emojis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.xml.stream.XMLStreamException;

import com.google.common.collect.Multimap;

class EmojiIndex {

    private final Multimap<String, String> keywordToEmojis;
    
    private EmojiIndex(Multimap<String, String> keywordToEmojis) {
        this.keywordToEmojis = keywordToEmojis;
    }
    
    /**
     * Builds a keyword-to-emoji(s) index based on the annotations XML from the Unicode standard.
     */
    public static EmojiIndex buildIndex(InputStream inputStream) throws XMLStreamException, IOException {
        return new EmojiIndex(new EmojiParser().createKeywordIndex(inputStream));
    }
    
    public Collection<String> getEmojis(String keyword) {
        return keywordToEmojis.get(keyword);
    }
}
