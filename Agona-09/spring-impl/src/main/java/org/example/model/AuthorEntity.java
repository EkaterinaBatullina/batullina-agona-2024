package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private AuthorProfileEntity profile;

    @OneToMany(mappedBy = "author")
    private Set<BookEntity> books = new HashSet<>();
}
