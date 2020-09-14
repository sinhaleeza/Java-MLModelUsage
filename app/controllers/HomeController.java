package controllers;

import app.models.Publication;
import models.PublicationResults;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;

import java.util.*;

public class HomeController extends Controller {
    app.services.DBManipulation dbManipulation = new app.services.DBManipulation();
    List<Publication> publications;
    Map<String, Set<String>> categoryMap = new HashMap<>();
    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result index() {
        System.out.println("Create results objects");
        dbManipulation.connectDB();
        publications = dbManipulation.fetchPublication();
        for(Publication publication:publications){
            List<String> categories =  new ArrayList<>();
            String category = publication.getCategories().substring(1,publication.getCategories().length()-1);
            if(category.contains(",")){
                String[] categoriesList = category.split(",");
                for(int i=0; i< categoriesList.length; i++){
                    categories.add(categoriesList[i].trim());
                }
            }else{
                categories.add(category.trim());
            }
            System.out.println("Publication : "+publication.getId()+" : categories : "+categories.toString());
            Set<String> papers = new HashSet<>();
            for(String categoryItem:categories){
                if(categoryMap.keySet().contains(categoryItem)){
                    papers = categoryMap.get(categoryItem);
                }
                papers.add(publication.getId()+"|"+publication.getTitle().replace(',',';'));
                categoryMap.put(categoryItem,papers);
            }
        }
        dbManipulation.disconnectDB();
        Map<String, Set<String>> sortedCategoryMap = new TreeMap<String, Set<String>>(categoryMap);
        System.out.println("Category Map size : "+categoryMap.size());
        return ok(views.html.home.render(sortedCategoryMap));
    }

    public Result paperList(){
        Form categoryForm = formFactory.form().bindFromRequest();
        String papers = ((String)categoryForm.rawData().get("papers")).substring(1,((String) categoryForm.rawData().get("papers")).length()-1);
        System.out.println("raw data : "+papers);
        String[] papersList = papers.split(",");
        System.out.println("inside route: "+papersList.length);
        publications = new ArrayList<>();
        publications.add(new Publication(Integer.toString(Math.abs(new Random().nextInt()))));
        Set<String> papersSet = new HashSet<>();
        for(int i = 0 ; i<papersList.length ; i++){
            papersSet.add(papersList[i]);
        }
        return ok(views.html.paperList.render(papersSet));
    }

    public Result paperDetails(){
        String paperId = request().getQueryString("paperId");
        System.out.println("paperId : "+paperId);
        dbManipulation.connectDB();
        Publication publication = dbManipulation.fetchSelectedPublication(paperId);
        dbManipulation.disconnectDB();
        PublicationResults publicationResults = new PublicationResults();
        List<Publication> publications = new ArrayList<>();publications.add(publication);
        publicationResults.setPublications(publications);
        return ok(views.html.paperDetails.render(publicationResults));
    }
}
