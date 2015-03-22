package com.razor.blogger.providers.mongo;

import com.google.gson.Gson;
import org.bson.Document;

public class SimpleMongoModelAdapter<T> implements MongoModelAdapter<T> {

    Gson gson = null;
    Class<T> clazz;

    public SimpleMongoModelAdapter(Class<T> classOfT) {
        this.gson = new Gson();
        this.clazz = classOfT;
    }

    @Override
    public T fromDocument(Document document) {
        return this.gson.fromJson(document.toJson(), this.clazz);
    }

    @Override
    public Document toDocument(T model) {
        return Document.parse(new Gson().toJson(model));
    }
}
