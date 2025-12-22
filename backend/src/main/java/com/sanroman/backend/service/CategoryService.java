package com.sanroman.backend.service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
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
        try {
            return repo.findAll().stream().map(c -> c.toDTO()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Category getById(Long id) {
        try {
            return repo.findById(id).orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Category not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Category getByName(String name) {
        try {
            return repo.findByCategory(name);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<CategoryDTO> getAllUsedCategories() {
        try {
            return repo.findAllUsedCategories().stream().map(c -> c.toDTO()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
