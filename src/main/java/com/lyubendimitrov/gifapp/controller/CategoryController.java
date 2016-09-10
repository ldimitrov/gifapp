package com.lyubendimitrov.gifapp.controller;

import com.lyubendimitrov.gifapp.data.CategoryRepository;
import com.lyubendimitrov.gifapp.data.GifRepository;
import com.lyubendimitrov.gifapp.model.Category;
import com.lyubendimitrov.gifapp.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author <a href="mailto:lyuben.dimitrov@comsysto.com">dimitrov</a>
 * @since 10.09.2016
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GifRepository gifRepository;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String listCategories(ModelMap modelMap) {
        List<Category> categories = categoryRepository.findAllCategories();
        modelMap.put("categories", categories);
        return "categories";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String category(@PathVariable int id, ModelMap modelMap) {
        Category category = categoryRepository.findById(id);
        modelMap.put("category", category);

        List<Gif> gifs = gifRepository.findByCategoryId(id);
        modelMap.put("gifs", gifs);

        return "category";
    }
}
