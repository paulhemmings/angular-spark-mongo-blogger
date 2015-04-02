package com.razor.blogger;

import com.mongodb.MongoClient;
import com.razor.blogger.configuration.PersistenceConfig;
import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import com.razor.blogger.providers.ProviderFactory;
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

        ModelProvider<BlogModel> modelProvider = new ProviderFactory().buildProvider(persistenceConfig, client, persistenceConfig.mongoDatabase(), "blogs", BlogModel.class);
        new BlogResource(new BlogService(modelProvider));
    }

    /**
     * Get configuration for persistence layer
     * @return
     */

    private static PersistenceConfig buildPersistenceConfig() {
        return ConfigFactory.create(PersistenceConfig.class);
    }
}
