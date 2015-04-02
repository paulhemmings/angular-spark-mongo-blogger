package com.razor.blogger.providers;

import org.bson.types.ObjectId;

import java.util.List;

public interface ModelProvider<T> {
    List<T> findAll();
    T findById(String id);
    T update(T model, String id);
}
