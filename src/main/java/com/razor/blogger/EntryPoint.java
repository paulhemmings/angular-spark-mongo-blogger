package com.razor.blogger;

import com.mongodb.MongoClient;
import com.razor.blogger.configuration.PersistenceConfig;
import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import com.razor.blogger.providers.jongo.BlogProvider;
import com.razor.blogger.providers.jongo.JongoProvider;
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
        new BlogResource(new BlogService(buildProvider(persistenceConfig, client, persistenceConfig.mongoDatabase())));
    }

    /*
     * Build provider based on configuration
     * Only works because we're building provider for single model
     */

    private static ModelProvider<BlogModel> buildProvider(PersistenceConfig persistenceConfig,
                                                          MongoClient client,
                                                          String database) throws Exception {
        ModelProvider<BlogModel> provider = null;
        switch (persistenceConfig.mongoProvider()) {
            case "mongo":
                provider = buildMongoProvider(client, database);
                break;
            case "jongo":
                provider = buildJongoProvider(client, database);
                break;
            case "morphia":
                break;
            default:
                throw new Exception("Invalid provider configuration");
        }
        return provider;
    }

    /**
     * Use the Jongo ORM
     * @param client
     * @return
     */

    private static ModelProvider<BlogModel> buildJongoProvider(MongoClient client, String database) {
        return new JongoProvider<>(client.getDB(database), "blogs", BlogModel.class);
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
