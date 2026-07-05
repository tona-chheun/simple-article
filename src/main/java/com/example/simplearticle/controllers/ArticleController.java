package com.example.simplearticle.controllers;

import com.example.simplearticle.models.Article;
import com.example.simplearticle.services.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("article", new Article());
        return "articles/create";
    }

    @PostMapping
    public String store(@ModelAttribute Article article) {
        articleService.create(article);
        return "redirect:/articles";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        return "articles/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Article article) {
        articleService.update(id, article);
        return "redirect:/articles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleService.delete(id);
        return "redirect:/articles";
    }
}
