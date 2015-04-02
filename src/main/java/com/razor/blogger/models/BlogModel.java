package com.razor.blogger.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity("blog")
public class BlogModel {
    private ObjectId _id;
    private String date;
    private String[] tags;
    private String title;
    private String content;



    public ObjectId getId() {
        return _id;
    }

    public BlogModel setId(ObjectId id) {
        this._id = id;
        return this;
    }

    public String getDate() {
        return date;
    }

    public BlogModel setDate(String date) {
        this.date = date;
        return this;
    }

    public String[] getTags() {
        return tags;
    }

    public BlogModel setTags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlogModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BlogModel setContent(String content) {
        this.content = content;
        return this;
    }
}
