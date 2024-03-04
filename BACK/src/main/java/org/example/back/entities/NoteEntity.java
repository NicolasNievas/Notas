package org.example.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_note;

    @Column
    private String content;

    @Column
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "note_tag",
            joinColumns = @JoinColumn(name = "id_note"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<TagEntity> tags = new HashSet<>();
}
