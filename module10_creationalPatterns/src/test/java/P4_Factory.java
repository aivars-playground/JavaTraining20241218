import org.junit.jupiter.api.Test;
import org.w3c.dom.DocumentType;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class P4_Factory {

    @Test
    void factory() {

        var data = new Data("aaaa", List.of("p1", "p2"), LocalDate.now());
        var doc1 = PageFactory.getDocument(PageFactory.DocumentType.XML_FO, Locale.ENGLISH);
        var doc2 = PageFactory.getDocument(PageFactory.DocumentType.XML_FO, Locale.of("en", "US"));
        var doc3 = PageFactory.getDocument(PageFactory.DocumentType.HTML, Locale.ENGLISH);


        System.out.println(doc1 + " -> " + doc1.body(data));
        System.out.println(doc2 + " -> " + doc2.body(data));
        System.out.println(doc3 + " -> " + doc3.body(data));

    }

}


record Data(String title, List<String> paragraphs, LocalDate date) implements Cloneable {}

interface Document {
    String body(Data data);
}


class PageFactory {

    public static Document getDocument(DocumentType documentType, Locale locale) {
        Document document = null;
        switch (documentType) {
            case HTML -> document = new HtmlTemplate(locale.getDisplayLanguage());
            case XML_FO -> document = new XMLFOTemplate(locale);
        }
        return document;
    }

    enum DocumentType {
        XML_FO, HTML
    }

    private static class HtmlTemplate implements Document {
        private final String language;
        HtmlTemplate(String language) {
            this.language = language;
        }
        @Override
        public String body(Data data) {
            return new StringBuilder()
                    .append("<html>")
                    .append("<head><title>"+data.title()+"</title></head>")
                    .append("<body>")
                    .append(data.paragraphs().stream().map(l -> new StringBuffer().append("<p>").append(l).append("</p>").toString()).collect(Collectors.joining()))
                    .append("</body>")
                    .append("</html>")
                    .toString();

        }
    }

    private static class XMLFOTemplate implements Document {
        private final String language;
        private final String papersize;
        XMLFOTemplate(Locale locale) {
            this.language = locale.getLanguage();
            papersize = locale.getCountry() == "US" ? "Letter" : "A4";
        }
        @Override
        public String body(Data data) {
            return new StringBuilder()
                    .append("<fo:page lang='"+language+"' papersize='"+papersize+"'>")
                    .append("<fo:title><fo:text>"+data.title()+"</fo:text></fo:title>")
                    .append("<fo:flow>")
                    .append(data.paragraphs().stream().map(l -> new StringBuffer().append("<fo:text>").append(l).append("</fo:text>")))
                    .append("</fo:flow>")
                    .append("</fo:page>")
                    .toString();

        }
    }
}

