package com.lyubendimitrov.gifapp.web.controller;

import com.lyubendimitrov.gifapp.model.Gif;
import com.lyubendimitrov.gifapp.service.CategoryService;
import com.lyubendimitrov.gifapp.service.GifService;
import com.lyubendimitrov.gifapp.validator.GifValidator;
import com.lyubendimitrov.gifapp.web.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.lyubendimitrov.gifapp.web.ValidationMessage.Status.SUCCESS;

@Controller
public class GifController {

    @Autowired
    private GifService gifService;

    @Autowired
    private CategoryService categoryService;

    @InitBinder("gif")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new GifValidator());
    }

    @RequestMapping("/")
    public String listGifs(Model model) {
        List<Gif> gifs = gifService.findAll();

        model.addAttribute("gifs", gifs);
        return "gif/index";
    }

    @RequestMapping("/gifs/{gifId}")
    public String gifDetails(@PathVariable Long gifId, Model model) {
        Gif gif = gifService.findById(gifId);

        model.addAttribute("gif", gif);
        return "gif/details";
    }

    @RequestMapping("/gifs/{gifId}.gif")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long gifId) {
        return gifService.findById(gifId).getBytes();
    }

    @RequestMapping("/favorites")
    public String favorites(Model model) {
        List<Gif> faves = gifService.getFavorites();

        model.addAttribute("gifs", faves);
        model.addAttribute("username", "lapadets"); // Static username
        return "gif/favorites";
    }

    @RequestMapping(value = "/gifs", method = RequestMethod.POST)
    public String addGif(@Valid Gif gif, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gif", result);
            redirectAttributes.addFlashAttribute("gif", gif);
            return "redirect:/upload";
        }

        gifService.save(gif, gif.getFile());
        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("GIF uploaded successfully!", SUCCESS));
        return String.format("redirect:/gifs/%s", gif.getId());
    }

    @RequestMapping("/upload")
    public String formNewGif(Model model) {
        if (!model.containsAttribute("gif")) {
            model.addAttribute("gif", new Gif());
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "/gifs");
        model.addAttribute("heading", "Upload new GIF");
        model.addAttribute("submit", "Add");

        return "gif/form";
    }

    @RequestMapping(value = "/gifs/{gifId}/edit")
    public String formEditGif(@PathVariable Long gifId, Model model) {
        if (!model.containsAttribute("gif")) {
            model.addAttribute("gif", gifService.findById(gifId));
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", String.format("/gifs/%s", gifId));
        model.addAttribute("heading", "Edit GIF");
        model.addAttribute("submit", "Edit");

        return "gif/form";
    }

    @RequestMapping(value = "/gifs/{gifId}", method = RequestMethod.POST)
    public String updateGif(@Valid Gif gif, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gif", result);
            redirectAttributes.addFlashAttribute("gif", gif);
            return String.format("redirect:/gifs/%s/edit", gif.getId());
        }

        gifService.save(gif, gif.getFile());
        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("GIF was successfully edited!", SUCCESS));

        return String.format("redirect:/gifs/%s", gif.getId());
    }

    @RequestMapping(value = "/gifs/{gifId}/delete", method = RequestMethod.POST)
    public String deleteGif(@PathVariable Long gifId, RedirectAttributes redirectAttributes) {
        Gif gif = gifService.findById(gifId);
        gifService.delete(gif);
        redirectAttributes.addFlashAttribute("flash", new ValidationMessage("GIF deleted!", SUCCESS));

        return "redirect:/";
    }

    @RequestMapping(value = "/gifs/{gifId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long gifId) {
        Gif gif = gifService.findById(gifId);
        gifService.toggleFavourite(gif);

        return String.format("redirect:/gifs/%s", gif.getId());
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
        // TODO: Get list of GIFs whose description contains value specified by q
        List<Gif> gifs = new ArrayList<>();

        model.addAttribute("gifs", gifs);
        return "gif/index";
    }
}