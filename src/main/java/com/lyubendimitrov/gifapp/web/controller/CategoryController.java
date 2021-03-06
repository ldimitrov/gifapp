package com.lyubendimitrov.gifapp.web.controller;

import com.lyubendimitrov.gifapp.model.Category;
import com.lyubendimitrov.gifapp.service.CategoryService;
import com.lyubendimitrov.gifapp.web.Color;
import com.lyubendimitrov.gifapp.web.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static com.lyubendimitrov.gifapp.web.ValidationMessage.Status.FAILURE;
import static com.lyubendimitrov.gifapp.web.ValidationMessage.Status.SUCCESS;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Create");

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Edit");

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);

            return String.format("redirect:/categories/%s/edit", category.getId());
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("Category is successfully edited!", SUCCESS));

        return "redirect:/categories";
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            // Redirect back to the form
            return "redirect:/categories/add";
        }
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("Category is successfully added!", SUCCESS));

        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(categoryId);

        // TODO Implement logic to make it possible to delete a whole category including all gifs inside.
        if (category.getGifs().size() > 0) {
            redirectAttributes.addFlashAttribute("flash", new ValidationMessage("Only empty categories can be deleted", FAILURE));
            return String.format("redirect:/categories/%s", categoryId);
        }
        categoryService.delete(category);
        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("Category deleted!", SUCCESS));

        return "redirect:/categories";
    }
}
