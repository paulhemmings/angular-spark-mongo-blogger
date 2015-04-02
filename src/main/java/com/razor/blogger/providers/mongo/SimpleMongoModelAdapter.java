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

    /**
     * Create the model from a document. GSon does not know how to serialize
     * the ObjectId _id into a String. Take it out of the document and add it after
     * @param document
     * @return
     */

    @Override
    public T fromDocument(Document document) {
        String objectId = document.get("_id").toString();
        document.remove("_id");
        T model = this.gson.fromJson(document.toJson(), this.clazz);
        model.setId(objectId);
        return model;
    }

    /**
     * Convert the model to a Document. Remove the serialized "id' which has a value of String
     * and re-add it as a "_id" with a ObjectId value.
     * @param model
     * @return
     */

    @Override
    public Document toDocument(T model) {
        Document document = Document.parse(new Gson().toJson(model));
        document.remove("id");
        document.append("_id", new ObjectId(model.getId()));
        return document;
    }
}
