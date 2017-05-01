package com.lyubendimitrov.gifapp.repository;

import com.lyubendimitrov.gifapp.model.Gif;

import java.util.List;

public interface GifRepository {
    List<Gif> findAll();

    Gif findById(Long id);

    void save(Gif gif);

    void delete(Gif gif);
}
