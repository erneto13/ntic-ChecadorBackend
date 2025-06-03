package com.erneto13.ntic.service;

import com.erneto13.ntic.model.*;
import com.erneto13.ntic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    @Transactional
    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con id: " + id));
    }

    @Transactional
    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor updateProfessor(Long id, Professor professorDetails) {
        Professor professor = getProfessorById(id);
        professor.setName(professorDetails.getName());
        professor.setEmail(professorDetails.getEmail());
        professor.setName(professorDetails.getName());
        return professorRepository.save(professor);
    }

    @Transactional
    public void deleteProfessor(Long id) {
        Professor professor = getProfessorById(id);
        professorRepository.delete(professor);
    }

    @Transactional
    public void assignProfessorToSubject(Long professorId, Long subjectId) {
        Professor professor = getProfessorById(professorId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        professor.getSubjects().add(subject);
        professorRepository.save(professor);
    }

    @Transactional
    public void removeProfessorFromSubject(Long professorId, Long subjectId) {
        Professor professor = getProfessorById(professorId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        professor.getSubjects().remove(subject);
        professorRepository.save(professor);
    }

    @Transactional
    public Set<Subject> getProfessorSubjects(Long professorId) {
        Professor professor = getProfessorById(professorId);
        return professor.getSubjects();
    }
}