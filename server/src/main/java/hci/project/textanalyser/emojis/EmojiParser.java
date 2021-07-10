package hci.project.textanalyser.emojis;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder.SetMultimapBuilder;

class EmojiParser {

    public Multimap<String, String> createKeywordIndex(InputStream inputStream) throws XMLStreamException, IOException {
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        
        var index = SetMultimapBuilder.hashKeys()
                .treeSetValues()
                .<String, String>build();
        
        XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
        XMLStreamReader filteredReader = inputFactory.createFilteredReader(reader, new StreamFilter() {
            @Override
            public boolean accept(XMLStreamReader reader) {
                return XMLStreamReader.START_ELEMENT == reader.getEventType() && "annotation".equals(reader.getLocalName());
            }
        });
        
        while (filteredReader.hasNext()) {
            int next = filteredReader.next();
            
            if (XMLStreamReader.END_DOCUMENT == next) {
                // END_DOCUMENT always passes, ignoring the filter
                break;
            }
            String cp = null;
            String type = null;
            for (int i = 0; i < filteredReader.getAttributeCount(); i++) {
                String name = filteredReader.getAttributeLocalName(i);
                if ("cp".equals(name))
                    cp = filteredReader.getAttributeValue(i);
                if ("type".equals(name))
                    type = filteredReader.getAttributeValue(i);
            }
            
            String elementText = filteredReader.getElementText();
            if (!"tts".equals(type)) {
                String keywords = elementText;
                String emoji = cp;
                Stream.of(keywords.split("\\|")).map(String::trim).forEach(keyword -> index.put(keyword, emoji));
            }
        }
        
        return index;
    }
}
