package org.example.back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tag;

    @Column
    private String name;
}
