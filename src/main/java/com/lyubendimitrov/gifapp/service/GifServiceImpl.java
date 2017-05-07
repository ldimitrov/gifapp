package com.lyubendimitrov.gifapp.service;

import com.lyubendimitrov.gifapp.model.Gif;
import com.lyubendimitrov.gifapp.repository.GifRepository;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

    public static final Logger LOG = LoggerFactory.getLogger(GifServiceImpl.class);

    @Autowired
    private GifRepository gifRepository;

    @Override
    public List<Gif> findAll() {
        return gifRepository.findAll();
    }

    @Override
    public Gif findById(Long id) {
        return gifRepository.findById(id);
    }

    @Override
    public void save(Gif gif, MultipartFile file) {
        try {
            gif.setBytes(file.getBytes());
            gifRepository.save(gif);
            // Hash the id
            gif.setHash(new Hashids().encode(gif.getId()));
            gifRepository.save(gif);
        } catch (IOException e) {
            LOG.error("Unable to get byte array from uploaded file.");
        }
    }

    @Override
    public void update(Gif gif, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                gif.setBytes(file.getBytes());
            } catch (IOException e) {
                LOG.error("Unable to get byte array from uploaded file.");
            }
        } else {
            Gif oldGif = gifRepository.findById(gif.getId());
            gif.setBytes(oldGif.getBytes());
        }

        gifRepository.save(gif);
    }

    @Override
    public void delete(Gif gif) {
        gifRepository.delete(gif);
    }

    @Override
    public void toggleFavourite(Gif gif) {
        gif.setFavorite(!gif.isFavorite());
        gifRepository.save(gif);
    }

    public List<Gif> getFavorites() {
        List<Gif> faves = new ArrayList<>();

        // TODO Optimize
        for (Gif gif : gifRepository.findAll()) {
            if (gif.isFavorite()) {
                faves.add(gif);
            }
        }
        return faves;
    }
}
