package com.razor.blogger.models;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class MongoModel {

    @Id
    @ObjectId
    protected String id;

    public String getId() {
        return id;
    }

    public MongoModel setId(String id) {
        this.id = id;
        return this;
    }
}
