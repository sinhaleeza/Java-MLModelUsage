package services;

import app.models.Publication;
import models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AbstractXMLParser {

    /**
     * Read the xml file from the given location and initiate parsing
     */
    public ArrayList<Publication> parseXML(){
        ArrayList<Publication> publications = new ArrayList<Publication>();
        try {
            System.out.println("Starting parse xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("dblp_abstract_dataset.xml"));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            parseChannelNodes(nodeList,publications);
            System.out.println("publications count: "+publications.size());
        }catch (ParserConfigurationException ex){
            ex.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publications;
    }

    /**
     * Identify the immediate child nodes - channel nodes and send them further for parsing
     * @param nodeList - nodes under the dblp element
     */
    private void parseChannelNodes(NodeList nodeList,ArrayList<Publication> publications) {
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!"#text".equals(node.getNodeName())) {
                Publication publication = new Publication(Integer.toString(Math.abs(new Random().nextInt())));
                parseChildNodes(publication,node);
                System.out.println(publication.toString());
                publications.add(publication);
            }
        }
    }


    /**
     * Initiates parsing of the incollection, inproceedings, book and article nodes
     * @param publication - instantiated common meta data bean
     * @param node - the channel node
     */
    private void parseChildNodes(Publication publication, Node node) {
        NodeList childNodes = node.getChildNodes();
        Map<String,String> nodesMap = new HashMap<String, String>();
        for(int i = 0; i < childNodes.getLength(); i++){
            nodesMap.put(childNodes.item(i).getNodeName(), childNodes.item(i).getTextContent());
        }
        setNodeValuesInBeans(publication, nodesMap, node);
    }

    /**
     * Populates the publication bean with values inside channcel's the child nodes
     * Also, identifies the channel type and invokes appropriate method for bean population
     * @param publication - instantiated common meta data bean
     * @param nodesMap - map of node names and values for all child nodes inside this element
     * @param node - current channel node element
     */
    private void setNodeValuesInBeans(Publication publication, Map<String,String> nodesMap, Node node) {
        //Parse the conference papers nodes and populate the appropriate beans
        publication.setTitle(nodesMap.get("title"));
        publication.setEe(nodesMap.get("ee"));
        publication.setContent(nodesMap.get("abstract"));

        if(("inproceedings").equals(node.getNodeName())) {
            publication.setPublicationChannel("conference");
        }
        //Parse the article/journal papers nodes and populate the appropriate beans
        else if(("article").equals(node.getNodeName())){
            publication.setPublicationChannel("journal");
        }
    }


}
