package com.erneto13.ntic.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "professors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Professor extends User {
    @Column
    private String specialty;

    @Column
    private String department;
}

