package controllers;


import app.services.SOCPapersXMLParser;
import app.models.Publication;
import app.services.DBManipulation;
import java.io.IOException;
import java.util.ArrayList;

public class SOCPapersXMLController {

/**
 * The XML Parse Handler gets invoked when user selects to initiate parsing of the XML
 * for data storage in the DB
 * @throws IOException
 */
public static void main(String[] args){
    System.out.println("String parsing");
    SOCPapersXMLParser socPapersXMLParser = new SOCPapersXMLParser();
    DBManipulation dbManipulation = new DBManipulation();
    ArrayList<Publication> publications = socPapersXMLParser.parseXML();
    dbManipulation.connectDB();
    System.out.println("Publications size : "+publications.size());
    int count = 0 ;
    for(Publication publication:publications) {
        dbManipulation.updatePublicationWithMetaData(publication);
        System.out.println("Publication count : "+count++);

    }
    dbManipulation.disconnectDB();
}

}
