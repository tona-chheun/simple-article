package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(
            ArticleService articleService
    ) {
        this.articleService = articleService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("articles", this.articleService.getAll());
        return "articles/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("articleRequest", new ArticleRequest());
        return "articles/create";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute ArticleRequest articleRequest,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "articles/create";
        }

        articleService.create(articleRequest);
        return "redirect:/articles";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("articleRequest", articleService.findById(id));
        return "articles/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute ArticleRequest articleRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            articleRequest.setId(id);
            return "articles/edit";
        }

        articleService.update(id, articleRequest);
        return "redirect:/articles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/articles";
    }
}
