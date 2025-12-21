package com.sanroman.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanroman.backend.model.dto.CategoryDTO;
import com.sanroman.backend.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;


    @GetMapping("/")
    public List<CategoryDTO> getAllUsedCategories() {
        return service.getAllUsedCategories();
    }

}
