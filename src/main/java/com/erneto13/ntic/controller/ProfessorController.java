package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(professor);
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        Professor newProfessor = professorService.createProfessor(professor);
        return new ResponseEntity<>(newProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        Professor updatedProfessor = professorService.updateProfessor(id, professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/subjects")
    public Set<Subject> getProfessorSubjects(@PathVariable Long id) {
        return professorService.getProfessorSubjects(id);
    }

    @PostMapping("/{professorId}/subjects/{subjectId}")
    public ResponseEntity<Void> assignProfessorToSubject(
            @PathVariable Long professorId,
            @PathVariable Long subjectId) {
        professorService.assignProfessorToSubject(professorId, subjectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{professorId}/subjects/{subjectId}")
    public ResponseEntity<Void> removeProfessorFromSubject(
            @PathVariable Long professorId,
            @PathVariable Long subjectId) {
        professorService.removeProfessorFromSubject(professorId, subjectId);
        return ResponseEntity.ok().build();
    }
}