package org.example.back.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteResponse {
    private Long id;
    private String content;
    private boolean active;
    private Set<TagResponse> tags;
}
