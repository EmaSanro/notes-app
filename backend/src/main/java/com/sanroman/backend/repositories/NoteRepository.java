package com.sanroman.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanroman.backend.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByIsArchived(Boolean isArchived);

    @Query("select n from Note n order by created_at desc limit 8")
    List<Note> findByRecentNotes();

    @Query("select n from Note n join n.categories c where c.category = :category")
    List<Note> getAllByCategory(@Param("category") String category);
}
