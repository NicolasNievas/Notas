package org.example.back.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.back.dtos.NoteResponse;
import org.example.back.dtos.TagResponse;
import org.example.back.models.NoteRequest;
import org.example.back.models.TagRequest;
import org.example.back.services.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/notes")
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
    @PostMapping("/createNote")
    @Operation(summary = "Crear una nota", description = "Crea una nueva nota.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = challengeService.createNote(noteRequest);
        return ResponseEntity.ok(noteResponse);
    }
    @PutMapping("/updateNote/{noteId}")
    @Operation(summary = "Actualizar una nota", description = "Actualiza una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<NoteResponse> updateNote(@PathVariable long noteId, @RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = challengeService.updateNote(noteId, noteRequest);
        return ResponseEntity.ok(noteResponse);
    }
    @GetMapping("/getAllNotes")
    @Operation(summary = "Obtener todas las notas", description = "Obtiene todas las notas.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> allNoteResponses = challengeService.getAllNotes();
        return ResponseEntity.ok(allNoteResponses);
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener todas las notas activas", description = "Obtiene todas las notas activas.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<List<NoteResponse>> getAllActiveNotes() {
        List<NoteResponse> activeNoteResponses = challengeService.getAllActiveNotes();
        return ResponseEntity.ok(activeNoteResponses);
    }

    @GetMapping("/archived")
    @Operation(summary = "Obtener todas las notas archivadas", description = "Obtiene todas las notas archivadas.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<List<NoteResponse>> getAllArchivedNotes() {
        List<NoteResponse> archivedNoteResponses = challengeService.getAllArchivedNotes();
        return ResponseEntity.ok(archivedNoteResponses);
    }

    @PutMapping("/archive/{noteId}")
    @Operation(summary = "Archivar una nota", description = "Archiva una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No es posible archivar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<Void> archiveNote(@PathVariable long noteId) {
        challengeService.archiveNote(noteId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unarchive/{noteId}")
    @Operation(summary = "Desarchivar una nota", description = "Desarchiva una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No es posible desarchivar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<Void> unarchiveNote(@PathVariable long noteId) {
        challengeService.unarchiveNote(noteId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteNote/{noteId}")
    @Operation(summary = "Eliminar una nota", description = "Elimina una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No es posible eliminar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<Void> deleteNote(@PathVariable long noteId) {
        challengeService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{noteId}/add_tags")
    @Operation(summary = "Agregar etiquetas a una nota", description = "Agrega tags a una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<NoteResponse> addTagsToNote(
            @PathVariable long noteId,
            @RequestBody Set<Long> tagIds) {
        NoteResponse noteResponse = challengeService.addTagsToNote(noteId, tagIds);
        return ResponseEntity.ok(noteResponse);
    }

    @PostMapping("/{noteId}/remove_tags")
    @Operation(summary = "Eliminar etiquetas de una nota", description = "Elimina tags de una nota existente.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<NoteResponse> removeTagsFromNote(
            @PathVariable long noteId,
            @RequestBody Set<Long> tagIds) {
        NoteResponse noteResponse = challengeService.removeTagsFromNote(noteId, tagIds);
        return ResponseEntity.ok(noteResponse);
    }
    @GetMapping("/getNotesByTagId/{tagId}")
    @Operation(summary = "Obtener todas las notas por ID de etiqueta", description = "Obtiene todas las notas por ID de etiqueta.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class)))
    public ResponseEntity<List<NoteResponse>> getNotesByTagId(@PathVariable Long tagId) {
        List<NoteResponse> noteResponses = challengeService.getNotesByTagId(tagId);
        return ResponseEntity.ok(noteResponses);
    }


    @GetMapping("/getAllTags")
    @Operation(summary = "Obtener todas las etiquetas", description = "Obtiene todas las etiquetas.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<TagResponse> tagResponses = challengeService.getAllTags();
        return ResponseEntity.ok(tagResponses);
    }

    @GetMapping("/getNoteById/{id}")
    @Operation(summary = "Obtener una nota por ID", description = "Obtiene una nota por ID.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    @ApiResponse(responseCode = "400", description = "No se pudieron obtener los datos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse noteResponse = challengeService.getNoteById(id);
        return ResponseEntity.ok(noteResponse);
    }

    @PostMapping("/createTag")
    @Operation(summary = "Crear una etiqueta", description = "Crea una nueva etiqueta.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    @ApiResponse(responseCode = "400", description = "No se pudo crear la etiqueta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagRequest.class)))
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
        TagResponse tagResponse = challengeService.createTag(tagRequest);
        return ResponseEntity.ok(tagResponse);
    }
}
