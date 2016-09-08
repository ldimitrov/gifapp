package com.lyubendimitrov.gifapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}