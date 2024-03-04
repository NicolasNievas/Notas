package org.example.back.services.imp;


import org.example.back.dtos.NoteResponse;
import org.example.back.dtos.TagResponse;
import org.example.back.entities.NoteEntity;
import org.example.back.entities.TagEntity;
import org.example.back.models.NoteRequest;
import org.example.back.models.TagRequest;
import org.example.back.repositories.ChallengeRepository;
import org.example.back.repositories.TagRepository;
import org.example.back.services.ChallengeService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImp implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ModelMapper modelMapper;

    private final TagRepository tagRepository;

    public ChallengeServiceImp(ChallengeRepository challengeRepository, ModelMapper modelMapper, TagRepository tagRepository) {
        this.challengeRepository = challengeRepository;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        try {
            validateNoteRequest(noteRequest);

            NoteEntity noteEntity = modelMapper.map(noteRequest, NoteEntity.class);

            Set<TagEntity> tagEntities = processTagscreate(noteRequest.getTags());
            noteEntity.setTags(tagEntities);

            noteEntity.setActive(true);
            NoteEntity savedNoteEntity = challengeRepository.save(noteEntity);
            return mapToNoteResponse(savedNoteEntity);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar la nota en la base de datos.", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    public NoteResponse updateNote(long noteId, NoteRequest noteRequest) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);

        if (optionalNoteEntity.isPresent()) {
            NoteEntity noteEntity = optionalNoteEntity.get();

            if (noteRequest.getContent() != null && !noteRequest.getContent().isEmpty()) {
                noteEntity.setContent(noteRequest.getContent());

                NoteEntity savedNoteEntity = challengeRepository.save(noteEntity);
                return mapToNoteResponse(savedNoteEntity);
            } else {
                throw new IllegalArgumentException("El contenido de la nota no puede ser nulo o vacío");
            }
        } else {
            throw new RuntimeException("Nota no encontrada con ID: " + noteId);
        }
    }

    @Override
    public List<NoteResponse> getAllNotes() {
        List<NoteEntity> allNotes = challengeRepository.findAll();
        return allNotes.stream()
                .map(this::mapToNoteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getAllArchivedNotes() {
        List<NoteEntity> archivedNotes = challengeRepository.findByActiveFalse();
        return archivedNotes.stream()
                .map(this::mapToNoteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponse> getAllActiveNotes() {
        List<NoteEntity> activeNotes = challengeRepository.findByActiveTrue();
        return activeNotes.stream()
                .map(this::mapToNoteResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void archiveNote(long noteId) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);
        if (!optionalNoteEntity.isPresent()) {
            throw new RuntimeException("Note not found");
        }
        optionalNoteEntity.ifPresent(noteEntity -> {
            noteEntity.setActive(false);
            challengeRepository.save(noteEntity);
        });
    }

    @Override
    public void unarchiveNote(long noteId) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);
        if (!optionalNoteEntity.isPresent()) {
            throw new RuntimeException("Note not found");
        }
        optionalNoteEntity.ifPresent(noteEntity -> {
            noteEntity.setActive(true);
            challengeRepository.save(noteEntity);
        });
    }

    @Override
    public void deleteNote(long noteId) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);
        if (!optionalNoteEntity.isPresent()) {
            throw new RuntimeException("Note not found");
        }
        challengeRepository.deleteById(noteId);
    }



    @Override
    public NoteResponse addTagsToNote(long noteId, Set<Long> tagIds) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);

        if (optionalNoteEntity.isPresent()) {
            NoteEntity noteEntity = optionalNoteEntity.get();

            Set<TagEntity> newTags = convertToTagEntities(tagIds);
            noteEntity.getTags().addAll(newTags);

            NoteEntity savedNoteEntity = challengeRepository.save(noteEntity);
            return mapToNoteResponse(savedNoteEntity);
        } else {
            throw new RuntimeException("Nota no encontrada con ID: " + noteId);
        }
    }
    @Override
    public NoteResponse removeTagsFromNote(long noteId, Set<Long> tagIds) {
        Optional<NoteEntity> optionalNoteEntity = challengeRepository.findById(noteId);

        if (optionalNoteEntity.isPresent()) {
            NoteEntity noteEntity = optionalNoteEntity.get();
            Set<TagEntity> existingTags = noteEntity.getTags();

            if (existingTags != null && !existingTags.isEmpty()) {
                Set<TagEntity> tagsToDelete = processTags(tagIds);

                existingTags.removeAll(tagsToDelete);

                NoteEntity savedNoteEntity = challengeRepository.save(noteEntity);
                return mapToNoteResponse(savedNoteEntity);
            } else {
                throw new IllegalArgumentException("La nota con ID: " + noteId + " no tiene etiquetas asociadas.");
            }
        } else {
            throw new RuntimeException("Nota no encontrada con ID: " + noteId);
        }
    }



    @Override
    public List<NoteResponse> getNotesByTagId(Long tagId) {
        TagEntity tagEntity = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found"));

        Set<NoteEntity> noteEntities = challengeRepository.findByTags(tagEntity);
        return noteEntities.stream().map(this::mapToNoteResponse).collect(Collectors.toList());
    }


    @Override
    public List<TagResponse> getAllTags() {
        List<TagEntity> allTags = tagRepository.findAll();
        return allTags.stream()
                .map(tagEntity -> modelMapper.map(tagEntity, TagResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponse getNoteById(Long id) {
        NoteEntity noteEntity = challengeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        return mapToNoteResponse(noteEntity);
    }

    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        TagEntity tagEntity = modelMapper.map(tagRequest, TagEntity.class);
        TagEntity savedTagEntity = tagRepository.save(tagEntity);
        return mapToTagResponse(savedTagEntity);
    }


    private TagResponse mapToTagResponse(TagEntity tagEntity) {
        return modelMapper.map(tagEntity, TagResponse.class);
    }

    private NoteResponse mapToNoteResponse(NoteEntity noteEntity) {
        NoteResponse noteResponse = modelMapper.map(noteEntity, NoteResponse.class);

        if (noteResponse != null && noteResponse.getTags() != null) {
            noteResponse.getTags().forEach(tagResponse -> {
                if (tagResponse != null && tagResponse.getId_tag() == null) {
                    tagResponse.setId_tag(tagRepository.findByName(tagResponse.getName()).get().getId_tag());
                }
            });
        }

        return noteResponse;
    }

    private void validateNoteRequest(NoteRequest noteRequest) {
        if (noteRequest.getContent() == null || noteRequest.getContent().isEmpty()) {
            throw new IllegalArgumentException("El contenido de la nota no puede estar vacío.");
        }
    }
    private Set<TagEntity> processTags(Set<Long> tagIds) {
        Set<TagEntity> tagEntities = new HashSet<>();

        if (tagIds != null) {
            for (Long tagId : tagIds) {
                if (tagId != null) {
                    Optional<TagEntity> optionalTagEntity = tagRepository.findById(tagId);

                    if (optionalTagEntity.isPresent()) {
                        TagEntity tagEntity = optionalTagEntity.get();
                        tagEntities.add(tagEntity);
                    } else {
                        throw new IllegalArgumentException("La etiqueta con ID '" + tagId + "' no existe.");
                    }
                } else {
                    throw new IllegalArgumentException("ID de etiqueta no válido.");
                }
            }
        }

        return tagEntities;
    }

    private Set<TagEntity> processTagscreate(Set<TagRequest> tagRequests) {
        Set<TagEntity> tagEntities = new HashSet<>();

        if (tagRequests != null) {
            for (TagRequest tagRequest : tagRequests) {
                if (tagRequest != null && tagRequest.getName() != null && !tagRequest.getName().isEmpty()) {
                    Optional<TagEntity> optionalTagEntity = tagRepository.findByName(tagRequest.getName());

                    if (optionalTagEntity.isPresent()) {
                        TagEntity tagEntity = optionalTagEntity.get();
                        tagEntities.add(tagEntity);
                    } else {
                        throw new IllegalArgumentException("La etiqueta con nombre '" + tagRequest.getName() + "' no existe.");
                    }
                } else {
                    throw new IllegalArgumentException("Nombre de etiqueta no válido.");
                }
            }
        }

        return tagEntities;
    }


    private Set<TagEntity> convertToTagEntities(Set<Long> tagIds) {
        return tagIds.stream()
                .map(tagId -> {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setId_tag(tagId);
                    return tagEntity;
                })
                .collect(Collectors.toSet());
    }
}
