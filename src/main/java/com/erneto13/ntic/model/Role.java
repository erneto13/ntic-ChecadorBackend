package com.erneto13.ntic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ERole name;

    // Default constructor
    public Role() {}

    // Constructor from String
    public Role(String roleName) {
        this.name = ERole.valueOf(roleName);
    }

    public enum ERole {
        ADMIN,
        DEPARTMENT_HEAD,
        SUPERVISOR,
        PROFESSOR,
        STUDENT,
        GROUP_LEADER,
    }
}
