package com.razor.blogger.providers.mongo;

import com.google.gson.Gson;
import com.razor.blogger.models.MongoModel;
import org.bson.Document;
import org.bson.types.ObjectId;

public class SimpleMongoModelAdapter<T extends MongoModel> implements MongoModelAdapter<T> {

    Gson gson = null;
    Class<T> clazz;

    public SimpleMongoModelAdapter(Class<T> classOfT) {
        this.gson = new Gson();
        this.clazz = classOfT;
    }

    @Override
    public T fromDocument(Document document) {
        String objectId = document.get("_id").toString();
        document.remove("_id");
        T model = this.gson.fromJson(document.toJson(), this.clazz);
        model.setId(objectId);
        return model;
    }

    @Override
    public Document toDocument(T model) {
        Document document = Document.parse(new Gson().toJson(model));
        document.remove("id");
        document.append("_id", new ObjectId(model.getId()));
        return document;
    }
}
