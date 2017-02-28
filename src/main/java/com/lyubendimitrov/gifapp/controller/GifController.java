package com.lyubendimitrov.gifapp.controller;

import com.lyubendimitrov.gifapp.data.GifRepository;
import com.lyubendimitrov.gifapp.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class GifController {

    @Autowired
    private GifRepository gifRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listGifs(ModelMap modelMap) {
        List<Gif> allGifs = gifRepository.getAllGifs();
        modelMap.put("allGifs", allGifs);
        return "index";
    }

    @RequestMapping(value = "/gif/{name}", method = RequestMethod.GET)
    public String gifDetails(@PathVariable String name, ModelMap modelMap) {
        Date date = new Date();
        Gif gif = gifRepository.findByName(name);
        modelMap.put("gif", gif);
        return "gif-details";
    }
}