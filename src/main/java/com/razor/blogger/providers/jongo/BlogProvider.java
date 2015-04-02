package com.razor.blogger.providers.jongo;

import com.mongodb.DB;
import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class BlogProvider implements ModelProvider<BlogModel> {

    private MongoCollection blogs;

    public BlogProvider(DB db) {
        Jongo jongo = new Jongo(db);
        this.blogs = jongo.getCollection("blogs");
        this.initialize(this.blogs);
    }

    public long initialize(MongoCollection blogs) {
        if (blogs.count() < 1) {
            BlogModel blogModel = new BlogModel().setContent("test-content");
            blogs.insert(blogModel);
        }
        return blogs.count();
    }

    public List<BlogModel> findAll() {
        List<BlogModel> models = new ArrayList<>();
        MongoCursor<BlogModel> cursor = this.blogs.find().as(BlogModel.class);
        while (cursor.hasNext()) {
            BlogModel model = cursor.next();
            models.add(model);
        }
        return models;
    }

    public BlogModel findById(String id) {
        return this.find("_id", id);
    }

    @Override
    public BlogModel update(BlogModel model, ObjectId id) {
        return null;
    }

    public BlogModel find(String key, String value) {
        MongoCursor<BlogModel> blogCursor = this.blogs.find("{" + key + " : " + "'" + value + "'}").as(BlogModel.class);
        if (blogCursor.hasNext()) {
            return blogCursor.next();
        }
        return null;
    }

}
