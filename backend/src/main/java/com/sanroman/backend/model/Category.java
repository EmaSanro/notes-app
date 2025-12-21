package com.sanroman.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.sanroman.backend.model.dto.CategoryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String category;

    @ManyToMany(mappedBy = "categories")
    private List<Note> notes;

    public Category() {}

    public Category(String nombre) {
        this.category = nombre;
        this.notes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNote(List<Note> note) {
        this.notes = note;
    }

    public void addNote(Note note) {
        this.notes.add(note);
        note.getCategories().add(this);
    }

    // @Override
    // public String toString() {
    //     return "Category [id=" + id + ", category=" + category + "]";
    // }
    
    public CategoryDTO toDTO() {
        return new CategoryDTO(id, category);
    }
}
