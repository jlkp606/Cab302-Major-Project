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

        for(int i =0 ;i < list.getLength();i++){
            Node node = list.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String background = element.getAttribute("background");
                String message = element.getElementsByTagName("message").item(0).getTextContent();
                String info = element.getElementsByTagName("information").item(0).getTextContent();

                System.out.println(background);
                System.out.println(message);
                System.out.println(info);


            }
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        ReadXMLFile file = new ReadXMLFile();
        file.read();
    }
}
