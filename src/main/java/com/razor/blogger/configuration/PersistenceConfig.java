package com.razor.blogger.configuration;

import org.aeonbits.owner.Config;

public interface PersistenceConfig extends Config {

    @Config.Key("persistence.mongo.host")
    @DefaultValue("localhost")
    String mongoHost();

    @Config.Key("persistence.mongo.database")
    @DefaultValue("blogger")
    String mongoDatabase();

    @Config.Key("persistence.mongo.provider")
    @DefaultValue("mongo")
    String mongoProvider();
}
