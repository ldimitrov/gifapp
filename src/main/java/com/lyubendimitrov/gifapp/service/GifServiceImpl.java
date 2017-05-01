package com.lyubendimitrov.gifapp.service;

import com.lyubendimitrov.gifapp.model.Gif;
import com.lyubendimitrov.gifapp.repository.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GifServiceImpl implements GifService {

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
        } catch (IOException e) {
            // TODO LOGGING
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Gif gif) {
        gifRepository.delete(gif);
    }
}
