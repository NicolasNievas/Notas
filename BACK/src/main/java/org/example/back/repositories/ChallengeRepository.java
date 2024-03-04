package org.example.back.repositories;


import org.example.back.entities.NoteEntity;
import org.example.back.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ChallengeRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByActiveTrue();

    List<NoteEntity> findByActiveFalse();

    Set<NoteEntity> findByTags(TagEntity tagEntity);
}
