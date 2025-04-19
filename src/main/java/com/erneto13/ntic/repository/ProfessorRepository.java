package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.security.auth.Subject;
import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findBySpecialtiesContaining(Specialty specialty);
    List<Professor> findBySubjectsContaining(Subject subject);
}


