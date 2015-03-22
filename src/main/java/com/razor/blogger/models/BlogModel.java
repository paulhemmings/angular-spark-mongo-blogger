package com.razor.blogger.models;

import org.mongodb.morphia.annotations.Entity;

@Entity("blog")
public class BlogModel {
    public String _id;
    public String date;
    public String[] tags;
    public String title;
    public String content;
}
