package app.models;

import java.util.HashSet;
import java.util.Set;

public class Publication {
    private String title;
    private String publicationChannel;
    private String ee;
    private String content;
    private String id;
    private Set<String> authors = new HashSet<String>();
    private String time;
    private String malletId;
    private String categories;

    public Publication() {
    }

    public Publication(String id) {
        this.id = id;
    }

    public Publication(String title, String publicationChannel, String ee, String content, String id) {
        this.title = title;
        this.publicationChannel = publicationChannel;
        this.ee = ee;
        this.content = content;
        this.id = id;
    }

    public Publication(String title, String publicationChannel, String ee, String content, String id, Set<String> authors, String time, String malletId, String categories) {
        this.title = title;
        this.publicationChannel = publicationChannel;
        this.ee = ee;
        this.content = content;
        this.id = id;
        this.authors = authors;
        this.time = time;
        this.malletId = malletId;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationChannel() {
        return publicationChannel;
    }

    public void setPublicationChannel(String publicationChannel) {
        this.publicationChannel = publicationChannel;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMalletId() {
        return malletId;
    }

    public void setMalletId(String malletId) {
        this.malletId = malletId;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", publicationChannel='" + publicationChannel + '\'' +
                ", ee='" + ee + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", authors=" + authors +
                ", time='" + time + '\'' +
                ", malletId='" + malletId + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }
}
