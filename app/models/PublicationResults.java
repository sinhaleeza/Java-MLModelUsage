package models;

import app.models.Publication;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PublicationResults {
    List<app.models.Publication> publications;
    Map<String, Set<String>> categoryMap;

    public PublicationResults() {
    }

    public PublicationResults(List<Publication> publications, Map<String, Set<String>> categoryMap) {
        this.publications = publications;
        this.categoryMap = categoryMap;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public Map<String, Set<String>> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<String, Set<String>> categoryMap) {
        this.categoryMap = categoryMap;
    }

    @Override
    public String toString() {
        return "PublicationResults{" +
                "publications=" + publications.toString() +
                ", categoryMap=" + categoryMap.toString() +
                '}';
    }
}
