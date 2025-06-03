package com.erneto13.ntic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.HashSet;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "professors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends User {
    @ManyToMany
    @JoinTable(
            name = "professor_subjects",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonIgnoreProperties({"professors", "career"})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Subject> subjects = new HashSet<>();
}