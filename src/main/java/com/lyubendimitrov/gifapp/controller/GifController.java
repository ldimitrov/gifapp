package com.lyubendimitrov.gifapp.controller;

import com.lyubendimitrov.gifapp.model.Gif;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author <a href="mailto:lyuben.dimitrov@comsysto.com">dimitrov</a>
 * @since 07.09.2016
 */
@Controller
public class GifController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listGifs() {
        return "index";
    }

    @RequestMapping(value = "/gif", method = RequestMethod.GET)
    public String gifDetails(ModelMap modelMap) {
        Date date = new Date();
        Gif gif = new Gif("compiler-bot", date, "Lyuben", true);
        modelMap.put("gif", gif);
        return "gif-details";
    }
}