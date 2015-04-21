package com.razor.blogger.providers.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import com.razor.blogger.providers.ModelProvider;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoProvider<T> implements ModelProvider<T> {

    private MongoCollection<Document> collection;
    private MongoModelAdapter<T> adapter;

    public MongoProvider(MongoCollection<Document> collection, MongoModelAdapter<T> adapter) {
        this.collection = collection;
        this.adapter = adapter;
    }

    /**
     * Find all instances of model
     * @return
     */

    public List<T> findAll() {
        List<T> items = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                items.add(adapter.fromDocument(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    /**
     * Find by key/value pair
     * @param key
     * @param value
     * @return
     */

    public List<T> find(String key, String value) {
        List<T> items = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(eq(key, value)).iterator();
        try {
            while (cursor.hasNext()) {
                items.add(adapter.fromDocument(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    /**
     * Find document by ID
     * @param id
     * @return
     */

    public T findById(String id) {
        List<T> models = this.find("_id", id);
        return models.size() > 0 ? models.get(0) : null;
    }

    /**
     * Update model
     * @param model
     * @param id
     * @return
     */

    public T update(T model, String id) {
        if (id == null || id.isEmpty()) {
            collection.insertOne(adapter.toDocument(model));
        } else {
            BasicDBObject setter = new BasicDBObject();
            Document document = adapter.toDocument(model);
            document.remove("_id");
            setter.append("$set", document);
            collection.updateOne(eq("_id", new ObjectId(id)), setter);
        }
        return model;
    }
}

