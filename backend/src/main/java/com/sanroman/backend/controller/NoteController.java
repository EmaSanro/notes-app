package com.sanroman.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanroman.backend.model.dto.CreateCategoryDTO;
import com.sanroman.backend.model.dto.NoteCreateDTO;
import com.sanroman.backend.model.dto.NoteResponseDTO;
import com.sanroman.backend.model.dto.RemoveCategoriesDTO;
import com.sanroman.backend.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes API", description = "API to create your notes")
@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    NoteService service;

    @Operation(summary = "Active notes", description = "Return list of active notes")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "The list of active notes", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "204", description = "No content", content = {@Content(schema = @Schema())})
    })
    @GetMapping("") //This is the default method for viewing notes
    public List<NoteResponseDTO> getActiveNotes() {
        return service.getActiveNotes();
    }
    
    @Operation(summary = "Archive notes", description = "Return list of archive notes")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "The list of archive notes", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "204", description = "No content", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/archived")
    public List<NoteResponseDTO> getArchivedNotes() {
        return service.getArchivedNotes();
    }
    
    @Operation(summary = "Note by ID", description = "Get the note by specifying its id.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "The response of the specific note", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "404", description = "Not Found ", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public NoteResponseDTO getByID(
        @Parameter(required = true, in = ParameterIn.PATH, description = "ID of the note to search")
        @PathVariable Long id) {
        return service.getByID(id);
    }

    @Operation(summary = "Recent notes", description = "Returns the 8 most recent notes")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "The list of recent notes", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "204", description = "No content", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/most-recent")
    public List<NoteResponseDTO> getRecentNotes() {
        return service.getRecentNotes();
    }

    @GetMapping("/filterByCategory/{category}")
    public List<NoteResponseDTO> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    @Operation(summary = "Create note", description = "Creates the note and returns it.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "note created successfully", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        )
    })
    @PostMapping("")
    public NoteResponseDTO createNote(@RequestBody NoteCreateDTO noteDTO) {
        return service.createNote(noteDTO);
    }
    
    @Operation(summary = "Archive note", description = "archive the specific note using its ID")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Note filed successfully", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}/archive/{bool}")
    public NoteResponseDTO archiveNote(
        @Parameter(required = true, in = ParameterIn.PATH, description = "id of the note to archive")
        @PathVariable Long id, 
        @Parameter(required = true, in = ParameterIn.PATH, description = "Boolean to archive/unarchive (true, false)")
        @PathVariable Boolean bool) {
        return service.archiveNote(bool, id);
    }

    @Operation(summary = "Edit note", description = "Edit the note, being able to change the title and content")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Note updated succesfully", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}")
    public NoteResponseDTO editNote(
        @Parameter(required = true, in = ParameterIn.PATH, description = "Id of the note to edit")
        @PathVariable Long id, 
        @RequestBody NoteCreateDTO noteDTO) {
        return service.editNote(noteDTO, id);
    }

    @Operation(summary = "Add category", description = "Add category to the specific note")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Category added succesfully", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}/addCategory")
    public NoteResponseDTO addCategory(
        @Parameter(required = true, in = ParameterIn.PATH, description = "note id to add category")
        @PathVariable Long id, @RequestBody CreateCategoryDTO category) {
        return service.addCategory(id, category);
    }
    @Operation(summary = "Remove category", description = "Removes a category from a specific note")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Category removed succesfully", 
            content = {
                @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                mediaType = "application/json")
            }
        ),
        @ApiResponse(responseCode = "404", description = "Note/Category not found", content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{idNote}/removeCategory/{idCategory}")
    public NoteResponseDTO removeCategory(
        @Parameter(required = true, in = ParameterIn.PATH, description = "Id of the note")
        @PathVariable Long idNote,
        @Parameter(required = true, in = ParameterIn.PATH, description = "Id of the category")
        @PathVariable Long idCategory) {
        return service.removeCategory(idNote, idCategory);
    }
    @Operation(summary = "Remove categories", description = "Removes specific categories from a specific note")
        @ApiResponses({
            @ApiResponse(
                responseCode = "200", 
                description = "Categories removed succesfully", 
                content = {
                    @Content(schema = @Schema(implementation = NoteResponseDTO.class), 
                    mediaType = "application/json")
                }
            ),
            @ApiResponse(responseCode = "404", description = "Note not found", content = {@Content(schema = @Schema())})
        })
    @DeleteMapping("/{idNote}/categories")
    public NoteResponseDTO removeCategories(
        @Parameter(required = true, in = ParameterIn.PATH, description = "Id of the note")
        @PathVariable Long idNote,
        @RequestBody RemoveCategoriesDTO categoryIds) {
        return service.removeCategories(idNote, categoryIds.categoryIds());
    }

    @Operation(summary = "Delete note", description = "Delete note specifying its id")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Note deleted successfully", 
            content = {
                @Content(schema = @Schema())
            }
        ),
        @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    public Boolean deleteNote(
        @Parameter(required = true, in = ParameterIn.PATH, description = "note id to delete")
        @PathVariable Long id) {
        return service.deleteNote(id);
    }
}
