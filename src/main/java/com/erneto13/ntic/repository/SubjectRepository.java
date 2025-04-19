package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Career;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByName(String name);
    List<Subject> findByCareer(Career career);
    List<Subject> findByProfessorsContaining(Professor professor);
}
