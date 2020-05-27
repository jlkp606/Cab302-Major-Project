import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ReadXMLFile {
    public static final String xmlFilePath = System.getProperty("user.dir")+"/xmlFile.xml";
    public void read() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(xmlFilePath);

        NodeList list = document.getElementsByTagName("billboard");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String background = element.getAttribute("background");
                String message = element.getElementsByTagName("message").item(0).getTextContent();
                String info = element.getElementsByTagName("information").item(0).getTextContent();

                System.out.println(background);
                System.out.println(message);
                System.out.println(info);


            }
        }

        NodeList list_1 = document.getElementsByTagName("message");

        for (int i = 0; i < list_1.getLength(); i++) {
            Node node_1 = list_1.item(i);

            if (node_1.getNodeType() == Node.ELEMENT_NODE) {
                Element element_1 = (Element) node_1;
                String colour_message = element_1.getAttribute("colour");
                System.out.println(colour_message);
            }
        }


        NodeList list_2 = document.getElementsByTagName("information");

        for (int i = 0; i < list_2.getLength(); i++) {
            Node node_2 = list_2.item(i);

            if (node_2.getNodeType() == Node.ELEMENT_NODE) {
                Element element_2 = (Element) node_2;
                String colour_info = element_2.getAttribute("colour");
                System.out.println(colour_info);
            }
        }

        NodeList list_3 = document.getElementsByTagName("picture");

        for (int i = 0; i < list_3.getLength(); i++) {
            Node node_3 = list_3.item(i);

            if (node_3.getNodeType() == Node.ELEMENT_NODE) {
                Element element_3 = (Element) node_3;
                String image_data = element_3.getAttribute("data");
                String image_url = element_3.getAttribute("url");

                System.out.println(image_data);
                System.out.println(image_url);
            }
        }


    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        ReadXMLFile file = new ReadXMLFile();
        file.read();
    }
}
