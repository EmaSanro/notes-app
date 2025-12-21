package com.sanroman.backend.model.dto;

import java.time.LocalDate;
import java.util.List;

public record NoteResponseDTO(Long id, String title, String content, Boolean isArchived, LocalDate created_at, LocalDate updated_at, List<CategoryDTO> categories) { }
