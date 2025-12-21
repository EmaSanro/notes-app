package com.sanroman.backend.model.dto;

import java.util.List;

import com.sanroman.backend.model.Category;

public record NoteUpdateDTO(String title, String content, List<Category> categories) {
    
}
