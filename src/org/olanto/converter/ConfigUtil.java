/*
 * 
 */
package org.olanto.converter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 */
public class ConfigUtil {
    public static String configFile = "config.xml";
    
    public static String sourcePath;
    public static String targetFormat;
    public static String badPath;
    public static String docPath;
    public static String tempPath;    
    public static Integer maxRetry;
    public static Boolean keepExtension;            
    
    private static HashMap<String, ConverterPlugin> plugins = new HashMap<String, ConverterPlugin>();
    public static HashMap<String, Boolean> useBuiltin = new HashMap<String, Boolean>();    
    public static HashMap<String, ArrayList<ConverterPlugin>> mapping = new HashMap<String, ArrayList<ConverterPlugin>>();
    
    public static void loadConfigFromXml() throws ParserConfigurationException, SAXException, IOException, URISyntaxException{
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	Document doc = dbFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream(configFile));
	//optional, but recommended
	doc.getDocumentElement().normalize();
 
	Node node = doc.getElementsByTagName("targetFormat").item(0);
        targetFormat=node.getTextContent();
	node = doc.getElementsByTagName("sourcePath").item(0);
        sourcePath=node.getTextContent();
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
                        String name = element.getElementsByTagName("plugName").item(0).getTextContent();
                        String command = element.getElementsByTagName("plugCommand").item(0).getTextContent();
                        String process = element.getElementsByTagName("plugProcessWindows").item(0).getTextContent();
                        plugins.put(name, new ConverterPlugin(name,command,process));
                }
        }
    }
    
    public static void loadMaps(Document doc){
        NodeList nList = doc.getElementsByTagName("ext");
        
        for (int i = 0; i < nList.getLength(); i++) {
		Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
                        String ext=element.getElementsByTagName("extName").item(0).getTextContent();
                        int len=element.getElementsByTagName("conv").getLength();
                        for (int j=0;j<len;j++) {
                            String conv=element.getElementsByTagName("conv").item(j).getTextContent();
                            ConverterPlugin plugin=plugins.get(conv);
                            if (plugin!=null && mapping.get(ext)!=null) {
                                mapping.get(ext).add(plugin);
                            }
                        }
                        String builtin=element.getElementsByTagName("builtin").item(0).getTextContent();
                        if (Boolean.valueOf(builtin)) {
                            useBuiltin.put(ext, Boolean.TRUE);
                        } else {
                            useBuiltin.put(ext, Boolean.FALSE);
                        }                        
                }
        }
    }
    
    private static void validateConfiguration(){
        // check configuration ...
    }
}
