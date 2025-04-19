package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject newSubject = subjectService.createSubject(subject);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PostMapping("/with-career/{careerId}")
    public ResponseEntity<Subject> createSubjectWithCareer(
            @RequestBody Subject subject,
            @PathVariable Long careerId) {
        Subject newSubject = subjectService.createSubjectWithCareer(subject, careerId);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        Subject updatedSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{subjectId}/careers/{careerId}")
    public ResponseEntity<Void> assignSubjectToCareer(
            @PathVariable Long subjectId,
            @PathVariable Long careerId) {
        subjectService.assignSubjectToCareer(subjectId, careerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/professors")
    public Set<Professor> getSubjectProfessors(@PathVariable Long id) {
        return subjectService.getSubjectProfessors(id);
    }

    @GetMapping("/by-career/{careerId}")
    public List<Subject> getSubjectsByCareer(@PathVariable Long careerId) {
        return subjectService.getSubjectsByCareer(careerId);
    }
}
