package com.erneto13.ntic.controller;

import com.erneto13.ntic.dto.SubjectDTO;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subject) {
        SubjectDTO newSubject = subjectService.createSubject(subject);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PostMapping("/with-career/{careerId}")
    public ResponseEntity<SubjectDTO> createSubjectWithCareer(
            @RequestBody SubjectDTO subject,
            @PathVariable Long careerId) {
        SubjectDTO newSubject = subjectService.createSubjectWithCareer(subject, careerId);
        return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long id, @RequestBody SubjectDTO subject) {
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subject);
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
    public List<SubjectDTO> getSubjectsByCareer(@PathVariable Long careerId) {
        return subjectService.getSubjectsByCareer(careerId);
    }
}