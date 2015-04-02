package com.razor.blogger.providers.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import com.razor.blogger.providers.ModelProvider;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    public T find(String key, String value) {
        return adapter.fromDocument(collection.find(eq(key, value)).first());
    }

    /**
     * Find document by ID
     * @param id
     * @return
     */

    public T findById(String id) {
        return this.find("_id", id);
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
            UpdateResult result = collection.updateOne(eq("_id", new ObjectId(id)), setter);
            if (result.getMatchedCount() > 0) {
                Object updatedId = result.getUpsertedId();
            }
        }
        return model;
    }
}

