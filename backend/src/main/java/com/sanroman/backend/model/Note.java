package com.sanroman.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sanroman.backend.model.dto.NoteResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
@Schema(description = "Schema of Note")
public class Note {

    @Schema(accessMode = AccessMode.READ_ONLY, description = "Note id", example = "234")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Title of Note", example = "Clean the house")
    @Column
    private String title;

    @Schema(description = "Content of Note", example = "Clean the bathroom, rooms, kitchen, etc.")
    @Column
    private String content;

    @Schema(accessMode = AccessMode.READ_ONLY, description = "Date the note was created", example = "15-03-2025")
    @Column
    private LocalDate created_at;

    @Schema(description = "Date the note was updated", example = "16-03-2025")
    @Column
    private LocalDate updated_at;

    @Schema(description = "If the note is archived", example = "false")
    @Column
    private Boolean isArchived;

    @Schema(description = "List of categories/tags of the note", anyOf = Category.class)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories;

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.created_at = LocalDate.now();
        this.updated_at = null;
        this.isArchived = false;
        this.categories = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getNotes().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getNotes().remove(this);
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", title=" + title + ", content=" + content + ", created_at=" + created_at
                + ", updated_at=" + updated_at + ", isArchived=" + isArchived + "]";
    }

    public NoteResponseDTO toResponseDTO() {
        return new NoteResponseDTO(id, title, content, isArchived, created_at, updated_at, categories.stream().map(c -> c.toDTO()).toList());
    }
}
