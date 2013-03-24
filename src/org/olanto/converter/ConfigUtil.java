/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.olanto.converter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ang
 */
public class ConfigUtil {
    public static String configFile = "config.xml";
    
    public static String targetFormat;
    public static String badPath;
    public static String docPath;
    public static String tempPath;    
    public static Integer maxRetry;
    public static Boolean keepExtension;            
    
    public static void loadConfigFromXml() throws ParserConfigurationException, SAXException, IOException, URISyntaxException{
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	Document doc = dbFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream(configFile));
	//optional, but recommended
	doc.getDocumentElement().normalize();
 
	Node node = doc.getElementsByTagName("targetFormat").item(0);
        targetFormat=node.getTextContent();
	node = doc.getElementsByTagName("badPath").item(0);
        badPath=node.getTextContent();
	node = doc.getElementsByTagName("docPath").item(0);
        docPath=node.getTextContent();
	node = doc.getElementsByTagName("tempPath").item(0);
        tempPath=node.getTextContent();
	node = doc.getElementsByTagName("maxRetry").item(0);
        maxRetry=Integer.parseInt(node.getTextContent());
	node = doc.getElementsByTagName("keepExtension").item(0);
        keepExtension=Boolean.valueOf(node.getTextContent());
        
        loadPlugins(doc);
        loadMaps(doc);
        
        validateConfiguration();
    }
    
    private static void loadPlugins(Document doc){
        NodeList nList = doc.getElementsByTagName("plugin");
        
        for (int i = 0; i < nList.getLength(); i++) {
		Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
                        element.getElementsByTagName("plugName").item(0);
                        element.getElementsByTagName("plugCommand").item(0);
                        element.getElementsByTagName("plugProcessWindows").item(0);
                }
        }
    }
    
    public static void loadMaps(Document doc){
        NodeList nList = doc.getElementsByTagName("ext");
        
        for (int i = 0; i < nList.getLength(); i++) {
		Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
                        element.getElementsByTagName("extName").item(0);
                        element.getElementsByTagName("conv");
                        element.getElementsByTagName("builtin").item(0);
                }
        }
    }
    
    private static void validateConfiguration(){
        // check configuration ...
    }
}
