package com.razor.blogger.models;

// import org.jongo.marshall.jackson.oid.Id;
// import org.jongo.marshall.jackson.oid.ObjectId;
// import org.mongodb.morphia.annotations.Id;

public class MongoModel {

    @org.mongodb.morphia.annotations.Id
    @org.jongo.marshall.jackson.oid.Id
    @org.jongo.marshall.jackson.oid.ObjectId
    protected String id;

    public String getId() {
        return id;
    }

    public MongoModel setId(String id) {
        this.id = id;
        return this;
    }
}
