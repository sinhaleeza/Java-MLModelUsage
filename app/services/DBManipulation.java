package app.services;

import app.models.Publication;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import java.util.*;

public class DBManipulation {
    private MysqlDataSource dataSource = new MysqlDataSource();
    Connection connection = null;

    /**
     * DB Select/Insert Queries
     */

    public static final String PUBLICATION_SQL = "INSERT INTO PUBLICATION(title,ee,publicationChannel,content,id) VALUES(?,?,?,?,?)";
    public static final String PUBLICATION_UPDATE_CATEGORIES = "UPDATE  REVNITE.PUBLICATION SET malletId=?,categories=? WHERE id=?";
    public static final String PUBLICATION_UPDATE_METADATA = "UPDATE  REVNITE.PUBLICATION SET time=?,authors=? WHERE title=?";
    public static final String FETCH_PUBLICATION = "SELECT * FROM REVNITE.PUBLICATION";
    public static final String FETCH_SEL_PUBLICATION = "SELECT * FROM REVNITE.PUBLICATION where id=?";



    /**
     * Connect to the MYSQL DB
     */
    public void connectDB(){
        try {
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("revnite");
        connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect from the MYSQL DB
     */
    public void disconnectDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertIntoPublication(Publication publication){
        if (null != connection) {
                try {
                    PreparedStatement stmt = connection.prepareStatement(PUBLICATION_SQL);
                    stmt.setString(1, publication.getTitle());
                    stmt.setString(2, publication.getEe());
                    stmt.setString(3, publication.getPublicationChannel());
                    stmt.setString(4, publication.getContent());
                    stmt.setString(5, publication.getId());
                    int result = stmt.executeUpdate();
                }catch(SQLException e) {
                    e.printStackTrace();
                }

        }
    }

    public void updatePublicationWithCategories(String malletId,String categories,String paperId){
        if (null != connection) {
            try {
                PreparedStatement stmt = connection.prepareStatement(PUBLICATION_UPDATE_CATEGORIES);
                stmt.setString(1, malletId);
                stmt.setString(2, categories);
                stmt.setString(3, paperId);
                System.out.println(stmt.toString());
                int result = stmt.executeUpdate();
            }catch(SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void updatePublicationWithMetaData(Publication publication){
        if (null != connection) {
            try {
                PreparedStatement stmt = connection.prepareStatement(PUBLICATION_UPDATE_METADATA);
                stmt.setString(1, publication.getTime());
                stmt.setString(2, publication.getAuthors().toString());
                stmt.setString(3, publication.getTitle());
                System.out.println(stmt.toString());
                int result = stmt.executeUpdate();
            }catch(SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Publication> fetchPublication(){
        List<Publication> publications = new ArrayList<Publication>();
        if (null != connection) {
            try {
                PreparedStatement stmt = connection.prepareStatement(FETCH_PUBLICATION);
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    Set<String> authors = new HashSet<String>();authors.add(resultSet.getString("authors"));
                    Publication publication = new Publication(resultSet.getString("title"),
                            resultSet.getString("publicationChannel"),
                            resultSet.getString("ee"),
                            resultSet.getString("content"),
                            resultSet.getString("id"),
                            authors,
                            resultSet.getString("time"),
                            resultSet.getString("malletId"),
                            resultSet.getString("categories"));
                    publications.add(publication);
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Publications count : "+publications.size());
        return publications;
    }

    public Publication fetchSelectedPublication(String paperId){
        List<Publication> publications = new ArrayList<Publication>();
        System.out.println("Starting the db query");
        if (null != connection) {
            try {
                PreparedStatement stmt = connection.prepareStatement(FETCH_SEL_PUBLICATION);
                stmt.setString(1,paperId.trim());
                System.out.println(stmt.toString());
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    System.out.println("Got some results");
                    Set<String> authors = new HashSet<String>();authors.add(resultSet.getString("authors"));
                    Publication publication = new Publication(resultSet.getString("title"),
                            resultSet.getString("publicationChannel"),
                            resultSet.getString("ee"),
                            resultSet.getString("content"),
                            resultSet.getString("id"),
                            authors,
                            resultSet.getString("time"),
                            resultSet.getString("malletId"),
                            resultSet.getString("categories"));
                    publications.add(publication);
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Publications count : "+publications.size());
        return publications.get(0);
    }


}
