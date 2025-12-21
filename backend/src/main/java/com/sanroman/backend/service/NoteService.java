package com.sanroman.backend.service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sanroman.backend.model.Category;
import com.sanroman.backend.model.Note;
import com.sanroman.backend.model.dto.CreateCategoryDTO;
import com.sanroman.backend.model.dto.NoteCreateDTO;
import com.sanroman.backend.model.dto.NoteResponseDTO;
import com.sanroman.backend.repositories.NoteRepository;

@Service
public class NoteService {
    Logger log = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private NoteRepository repo;

    @Autowired
    private CategoryService catService;

    public List<NoteResponseDTO> getActiveNotes() {
        return repo.findByIsArchived(false).stream().map(note -> note.toResponseDTO()).toList();
    }
    
    public List<NoteResponseDTO> getArchivedNotes() {
        return repo.findByIsArchived(true).stream().map(note -> note.toResponseDTO()).toList();
    }

    public List<NoteResponseDTO> getRecentNotes() {
        return repo.findByRecentNotes().stream().map(n -> n.toResponseDTO()).toList();
    }

    public NoteResponseDTO getByID(Long id) {
        return repo.findById(id)
               .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note Not found with this id"))
               .toResponseDTO();
    }

    public List<NoteResponseDTO> getByCategory(String category) {
        try {
            return repo.getAllByCategory(category).stream().map(c -> c.toResponseDTO()).toList();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public NoteResponseDTO archiveNote(Boolean bool, Long id) { // This method is used to archive/unarchive notes
        Note note = repo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note Not found with this id"));
        try {
            note.setIsArchived(bool);
            repo.saveAndFlush(note);
            return note.toResponseDTO();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Unexpected Error");
        }
    }

    public NoteResponseDTO createNote(NoteCreateDTO note) {
        try {
            Note n = new Note(note.title(), note.content());
            return repo.save(n).toResponseDTO();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Unexpected Error");
        }
    }

    public NoteResponseDTO editNote(NoteCreateDTO note, Long id) {
        try {
            Note n = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note Not found with this id"));
            n.setTitle(note.title());
            n.setContent(note.content());
            n.setUpdated_at(LocalDate.now());
            return repo.saveAndFlush(n).toResponseDTO();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Unexpected Error");
        }
    }

    public Boolean deleteNote(Long id) {
        try {
            Note n = repo.findById(id)
                     .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "This note not exists"));
            repo.delete(n);
            return repo.findById(id).getClass() == Optional.class ? true : false;
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public NoteResponseDTO addCategory(Long id, CreateCategoryDTO category) {
        try {
            Note n = repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note Not Found"));
            Category cat = catService.getByName(category.name());
            if(cat == null) {
                 cat = new Category(category.name());
            }
            n.addCategory(cat);
            return repo.save(n).toResponseDTO();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public NoteResponseDTO removeCategory(Long idNote, Long idCategory) {
        try {
            Note n = repo.findById(idNote).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note not found"));
            Category c = catService.getById(idCategory);
            n.removeCategory(c);
            return repo.saveAndFlush(n).toResponseDTO();
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public NoteResponseDTO removeCategories(Long idNote, List<Long> categoryIds) {
        Note n = repo.findById(idNote).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Note not found"));

        n.getCategories().removeIf(category -> categoryIds.contains(category.getId()));

        return repo.saveAndFlush(n).toResponseDTO();
    }
}
