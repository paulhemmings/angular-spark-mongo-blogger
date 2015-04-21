package com.razor.blogger;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.razor.blogger.configuration.PersistenceConfig;
import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import com.razor.blogger.providers.ProviderFactory;
import com.razor.blogger.resources.BlogResource;
import com.razor.blogger.services.BlogService;
import org.aeonbits.owner.ConfigFactory;
import com.google.common.collect.Lists;
import spark.utils.StringUtils;

import java.util.ArrayList;

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
        MongoClient client = buildClient(persistenceConfig);

        ModelProvider<BlogModel> modelProvider = new ProviderFactory<BlogModel>().buildProvider(persistenceConfig, client, persistenceConfig.mongoDatabase(), "blogs", BlogModel.class);
        new BlogResource(new BlogService(modelProvider));
    }

    private static MongoClient buildClient(PersistenceConfig persistenceConfig) {

        if (StringUtils.isEmpty(persistenceConfig.mongoUser())) {
            return new MongoClient(persistenceConfig.mongoHost());
        }

        ArrayList<ServerAddress> serverAddresses
                = Lists.newArrayList(new ServerAddress(persistenceConfig.mongoHost()));

        ArrayList<MongoCredential> mongoCredentials
                = Lists.newArrayList(MongoCredential.createCredential(persistenceConfig.mongoUser(), persistenceConfig.mongoDatabase(), persistenceConfig.mongoPassword().toCharArray()));

        return new MongoClient(serverAddresses, mongoCredentials);
    }

    /**
     * Get configuration for persistence layer
     * @return
     */

    private static PersistenceConfig buildPersistenceConfig() {
        return ConfigFactory.create(PersistenceConfig.class);
    }
}
