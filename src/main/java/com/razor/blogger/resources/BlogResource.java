package com.razor.blogger.resources;

import com.razor.blogger.services.BlogService;

import static com.razor.blogger.utilities.JsonUtils.json;
import static spark.Spark.get;
import static spark.Spark.put;

public class BlogResource {

    private final BlogService blogService;

    public BlogResource(BlogService service) {
        this.blogService = service;
        buildEndpoints();
    }

    private void buildEndpoints() {

        // READ
        get("/blogs", "application/json", (request, response) ->
                        blogService.findAll(),
                json()
        );

        // UPDATE
        put("/blog", "application/json", (request, response) ->
                        blogService.update(request.body()),
                json()
        );
    }


}
