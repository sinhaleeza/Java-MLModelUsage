package app.services;


import app.models.Publication;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class SOCPapersXMLParser {

    /**
     * Read the xml file from the given location and initiate parsing
     */
    public ArrayList<Publication> parseXML(){
        ArrayList<Publication> publications = new ArrayList<Publication>();
        try {
            System.out.println("Starting parse xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("dblp-soc-papers-V2.xml");
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
            if(!"#text".equals(node.getNodeName()) && !"proceedings".equals(node.getNodeName())) {
                Publication publication = new Publication();
                parseChildNodes(publication,node);
                System.out.println(publication.toString());
                publications.add(publication);
            }
        }
        System.out.println("publications count: "+publications.size());
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
            if(("author").equals(childNodes.item(i).getNodeName())) {
                nodesMap.put(childNodes.item(i).getNodeName().concat(Integer.toString(i)), childNodes.item(i).getTextContent());
            }else{
                nodesMap.put(childNodes.item(i).getNodeName(), childNodes.item(i).getTextContent());
            }
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
        Iterator<String> nodeNamesItr = nodesMap.keySet().iterator();
        String nodeName = "";
        String nodeText = "";
        //Parse the conference papers nodes and populate the appropriate beans
        if(("inproceedings").equals(node.getNodeName()) || ("article").equals(node.getNodeName())) {
            publication.setTime(node.getAttributes().item(1).getNodeValue());
        }

        //Parsing the remaining child nodes for the super meta-data in the Publication bean
        while (nodeNamesItr.hasNext()) {
            nodeName = nodeNamesItr.next();
            nodeText = nodesMap.get(nodeName);
            if (nodeName.contains("author")) {
                publication.getAuthors().add(nodeText);
            } else if (nodeName.equals("title")) {
                publication.setTitle(nodeText);
            }
        }
    }



}
