package com.razor.blogger.providers;

import com.mongodb.MongoClient;
import com.razor.blogger.configuration.PersistenceConfig;
import com.razor.blogger.models.MongoModel;
import com.razor.blogger.providers.jongo.JongoProvider;
import com.razor.blogger.providers.mongo.MongoProvider;
import com.razor.blogger.providers.mongo.SimpleMongoModelAdapter;

public class ProviderFactory<T extends MongoModel> {

    /*
     * Build provider based on configuration
     */

    public ModelProvider<T> buildProvider(PersistenceConfig persistenceConfig,
                                                          MongoClient client,
                                                          String database,
                                                          String collection,
                                                          Class<T> clazz) throws Exception {
        ModelProvider provider = null;
        switch (persistenceConfig.mongoProvider()) {
            case "mongo":
                provider = buildMongoProvider(client, database, collection, clazz);
                break;
            case "jongo":
                provider = buildJongoProvider(client, database, collection, clazz);
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

    private ModelProvider<T> buildJongoProvider(MongoClient client, String database, String collection, Class<T> clazz) {
        return new JongoProvider<>(client.getDB(database), collection, clazz);
    }

    /**
     * Use the standard OOTB Mongo provider
     * @param client
     * @return
     */

    private ModelProvider<T> buildMongoProvider(MongoClient client, String database, String collection, Class<T> clazz ) {
        return new MongoProvider<>(client.getDatabase(database).getCollection(collection), new SimpleMongoModelAdapter<>(clazz));
    }
}
