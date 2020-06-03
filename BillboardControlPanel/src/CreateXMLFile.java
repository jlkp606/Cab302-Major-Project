
import java.io.File;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXMLFile {

    String title;
    String title_colour;
    String bg_colour;
    String message_bb;
    String message_colour;
    String image_data;
    String image_url;
    String xmlFilePath;
//    public static final String xmlFilePath = System.getProperty("user.dir")+"/billboard.xml";
    public CreateXMLFile(String billboard_title,String billboard_title_colour,String billboard_bg_colour, String billboard_message, String billboard_message_colour, String billboard_image_data,String billboard_image_url,String FilePath){
        title = billboard_title;
        title_colour = billboard_title_colour;
        bg_colour=billboard_bg_colour;
        message_bb = billboard_message;
        message_colour = billboard_message_colour;
        image_data = billboard_image_data;
        image_url = billboard_image_url;
        xmlFilePath = FilePath;
    }

    public  void start() throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("billboard");
        document.appendChild(root);

        Attr attr = document.createAttribute("background");
        attr.setValue(bg_colour);
        root.setAttributeNode(attr);

        Element info = document.createElement("message");
        info.appendChild(document.createTextNode(title));
        root.appendChild(info);

        Attr attr_info = document.createAttribute("colour");
        attr_info.setValue(title_colour);
        info.setAttributeNode(attr_info);

        Element picture = document.createElement("picture");
        root.appendChild(picture);
        if(image_url.equals("")) {
            Attr attr_data = document.createAttribute("data");
            attr_data.setValue(image_data);
            picture.setAttributeNode(attr_data);
        }

        if(image_data.equals("")) {
            Attr attr_url = document.createAttribute("url");
            attr_url.setValue(image_url);
            picture.setAttributeNode(attr_url);
        }

        Element message = document.createElement("information");
        message.appendChild(document.createTextNode(message_bb));
        root.appendChild(message);

        Attr attr_mess = document.createAttribute("colour");
        attr_mess.setValue(message_colour);
        message.setAttributeNode(attr_mess);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        transformer.transform(domSource, streamResult);

    }



}