package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    Category findById(Long id);

    void save (Category category);

    void delete (Category category);
}
