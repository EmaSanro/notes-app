package com.sanroman.backend.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sanroman.backend.model.Category;
import com.sanroman.backend.model.dto.CategoryDTO;
import com.sanroman.backend.repositories.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public List<CategoryDTO> getAll() {
        return repo.findAll().stream().map(c -> c.toDTO()).toList();
    }

    public Category getById(Long id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Category not found"));
    }

    public Category getByName(String name) {
        return repo.findByCategory(name);
    }

    public List<CategoryDTO> getAllUsedCategories() {
        return repo.findAllUsedCategories().stream().map(c -> c.toDTO()).toList();
    }
}
