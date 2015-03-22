package com.razor.blogger.services

import com.razor.blogger.models.BlogModel
import com.razor.blogger.providers.ModelProvider
import spock.lang.Specification

class BlogServiceSpec extends Specification {

    def "it should store values passed in constructor"() {
        given:
            ModelProvider<BlogModel> provider = Mock();
            BlogService blogService = new BlogService(provider);

        when:
            ModelProvider<BlogModel> storedProvider = blogService.getProvider();

        then:
            storedProvider == provider;
    }

    def "calling findAll should call correct provider method"() {
        given:
            ModelProvider<BlogModel> provider = Mock();
            BlogService blogService = new BlogService(provider);
            List<BlogModel> results = new ArrayList<BlogModel>();

        when:
            List<BlogModel> modelList = blogService.findAll();

        then:
            1 * provider.findAll() >> results;
            modelList == results;
    }

}
