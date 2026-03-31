package com.blog.Bloggingproject.controller;

import com.blog.Bloggingproject.model.Post;
import com.blog.Bloggingproject.repository.PostRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    @Autowired
    private PostRepo repo;

    // Dashboard → show posts (or summary)
    @GetMapping({"/Dashboard"})
    public String Dashboard(Model model) {
        model.addAttribute("Dashboard", repo.findAll());
        return "Dashboard";  // shows index.html
    }

    // Blog Posts → same as dashboard for now
    @GetMapping("/posts")
    public String viewPosts(Model model) {
        model.addAttribute("listPosts", repo.findAll());
        return "index";  // reuse index.html
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute Post post) {
        repo.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model) {
        Post post = repo.findById(id).orElse(null);
        model.addAttribute("post", post);
        return "edit_post";
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute("post") Post post) {
        repo.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/posts";
    }

    // Dummy pages for analytics & settings
    @GetMapping("/analytics")
    public String analytics() {
        return "analytics"; // create analytics.html
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings"; // create settings.html
    }
}
