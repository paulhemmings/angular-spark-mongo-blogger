package com.razor.blogger.services;

import com.razor.blogger.models.BlogModel;
import com.razor.blogger.providers.ModelProvider;
import com.razor.blogger.providers.jongo.BlogProvider;

import java.util.List;

public class BlogService {

    private final ModelProvider<BlogModel> provider;

    public BlogService(ModelProvider<BlogModel> provider) {
        this.provider = provider;
    }

    public ModelProvider<BlogModel> getProvider() { return this.provider; }

    public List<BlogModel> findAll() {
        return this.getProvider().findAll();
    }

    public BlogModel findById(String id) {
        return this.getProvider().findById(id);
    }

}
