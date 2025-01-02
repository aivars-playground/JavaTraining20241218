import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class P5_AbstractFactory {

    @Test
    void abstractFactory() throws ParserConfigurationException, IOException, SAXException {

        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <document>
                <body>
                AAAA
                </body>
                </document>
                """;

        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

        DocumentBuilderFactory abstractFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder factory = abstractFactory.newDocumentBuilder();

        Document document = factory.parse(bais);
        document.getDocumentElement().normalize();

        System.out.println(document.getDocumentElement());

        System.out.println(abstractFactory); //chooses apache.xerces impl
        System.out.println(factory);

    }

}

