package app.controllers;

import app.services.DBManipulation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MalletController {

/**
 * The mallet composition file is parsed
 * for data storage in the DB
 * @throws IOException
 */
public static void main(String[] args){
    System.out.println("String parsing mallet file");
    DBManipulation dbManipulation = new DBManipulation();
    dbManipulation.connectDB();
    double max = 0.0;
    String currentLine;
    List<String> categoriesList;
    try {
        BufferedReader reader = new BufferedReader(new FileReader(new File("mallet_composition.txt")));
        while((currentLine = reader.readLine())!= null){
            max = 0.0;
            categoriesList = new ArrayList<String>();
            String[] elements = currentLine.split("\\s+");
            String[] paperText = elements[1].split("/");
            String paperId = paperText[paperText.length-1];
            paperId = paperId.substring(0,paperId.length()-4);
            for(int i = 2;i<elements.length;i++){
                double element = Double.parseDouble(elements[i]);
                if(element > max){
                    max = element;
                }
            }
            System.out.println();
            System.out.print("max probablity : "+elements[0]+" : "+paperId+" : "+max);
            for(int i = 2;i<elements.length;i++){
                double element = Double.parseDouble(elements[i]);
                if(element == max){
                    categoriesList.add("Category"+(i-1));
                }
            }
            System.out.print(" : Categories : "+ categoriesList.toString());
            dbManipulation.updatePublicationWithCategories(elements[0],categoriesList.toString(),paperId);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    dbManipulation.disconnectDB();
}

}
