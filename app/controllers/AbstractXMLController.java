package controllers;



import app.models.Publication;
import services.AbstractXMLParser;
import app.services.DBManipulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AbstractXMLController {

/**
 * The XML Parse Handler gets invoked when user selects to initiate parsing of the XML
 * for data storage in the DB
 * @throws IOException
 */
public static void main(String[] args){
    System.out.println("String parsing");
    AbstractXMLParser abstractXmlParser = new AbstractXMLParser();
    DBManipulation dbManipulation = new DBManipulation();
    ArrayList<Publication> publications = abstractXmlParser.parseXML();
    dbManipulation.connectDB();
    System.out.println("Publications size : "+publications.size());
    int count = 0 ;
    for(Publication publication:publications) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("abstract/" + publication.getId() + ".txt")));
            writer.write(publication.getContent());
            dbManipulation.insertIntoPublication(publication);
            System.out.println("Publication count : "+count++);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    dbManipulation.disconnectDB();
}

}
