package com.razor.blogger;

import com.mongodb.MongoClient;
import com.razor.blogger.configuration.PersistenceConfig;
import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import com.razor.blogger.providers.jongo.BlogProvider;
import com.razor.blogger.providers.mongo.MongoProvider;
import com.razor.blogger.providers.mongo.SimpleMongoModelAdapter;
import com.razor.blogger.resources.BlogResource;
import com.razor.blogger.services.BlogService;
import org.aeonbits.owner.ConfigFactory;

import static spark.SparkBase.staticFileLocation;

public class EntryPoint {

    /**
     * Hit it!
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception{
        staticFileLocation("/public");
        PersistenceConfig persistenceConfig = buildPersistenceConfig();
        MongoClient client = new MongoClient(persistenceConfig.mongoHost());
        new BlogResource(new BlogService(buildJongoProvider(client, persistenceConfig.mongoDatabase())));
    }

    /**
     * Use the Jongo ORM
     * @param client
     * @return
     */

    private static ModelProvider<BlogModel> buildJongoProvider(MongoClient client, String database) {
        return new BlogProvider(client.getDB(database));
    }

    /**
     * Use the standard OOTB Mongo provider
     * @param client
     * @return
     */

    private static ModelProvider<BlogModel> buildMongoProvider(MongoClient client, String database) {
        return new MongoProvider<>(client.getDatabase(database).getCollection("blogs"), new SimpleMongoModelAdapter<>(BlogModel.class));
    }

    /**
     * Get configuration for persistence layer
     * @return
     */

    private static PersistenceConfig buildPersistenceConfig() {
        return ConfigFactory.create(PersistenceConfig.class);
    }
}
