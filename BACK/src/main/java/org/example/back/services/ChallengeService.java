package org.example.back.services;

import org.example.back.dtos.NoteResponse;
import org.example.back.dtos.TagResponse;
import org.example.back.models.NoteRequest;
import org.example.back.models.TagRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ChallengeService {
    NoteResponse createNote(NoteRequest noteRequest);
    NoteResponse updateNote(long noteId, NoteRequest noteRequest);
    List<NoteResponse> getAllNotes();
    List<NoteResponse> getAllArchivedNotes();
    List<NoteResponse> getAllActiveNotes();
    void archiveNote(long noteId);
    void unarchiveNote(long noteId);
    void deleteNote(long noteId);
    NoteResponse addTagsToNote(long noteId, Set<Long> tagIds);
    NoteResponse removeTagsFromNote(long noteId, Set<Long> tagIds);
    List<NoteResponse> getNotesByTagId(Long tagId);
    List<TagResponse> getAllTags();
    NoteResponse getNoteById(Long id);
    TagResponse createTag(TagRequest tagRequest);
}
