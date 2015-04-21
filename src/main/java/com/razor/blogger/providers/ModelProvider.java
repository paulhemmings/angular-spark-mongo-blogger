package com.razor.blogger.providers;

import org.bson.types.ObjectId;

import java.util.List;

public interface ModelProvider<T> {
    List<T> findAll();
    List<T> find(String key, String value);
    T findById(String id);
    T update(T model, String id);
}
