package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Syllabus;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.service.CourseService;
import com.erneto13.ntic.service.SyllabusService;
import com.erneto13.ntic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/syllabi")
public class SyllabusController {

    @Autowired
    private SyllabusService syllabusService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    // Obtener todos los planes de estudio
    @GetMapping
    public ResponseEntity<List<Syllabus>> getAllSyllabi() {
        List<Syllabus> syllabi = syllabusService.getAllSyllabi();
        return new ResponseEntity<>(syllabi, HttpStatus.OK);
    }

    // Obtener plan de estudio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Syllabus> getSyllabusById(@PathVariable Long id) {
        Optional<Syllabus> syllabus = syllabusService.getSyllabusById(id);
        return syllabus.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nuevo plan de estudio
    @PostMapping
    public ResponseEntity<Syllabus> createSyllabus(@RequestBody Syllabus syllabus) {
        Syllabus savedSyllabus = syllabusService.saveSyllabus(syllabus);
        return new ResponseEntity<>(savedSyllabus, HttpStatus.CREATED);
    }

    // Actualizar plan de estudio existente
    @PutMapping("/{id}")
    public ResponseEntity<Syllabus> updateSyllabus(@PathVariable Long id, @RequestBody Syllabus syllabus) {
        if (!syllabusService.getSyllabusById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        syllabus.setId(Math.toIntExact(id));
        Syllabus updatedSyllabus = syllabusService.saveSyllabus(syllabus);
        return new ResponseEntity<>(updatedSyllabus, HttpStatus.OK);
    }

    // Eliminar plan de estudio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSyllabus(@PathVariable Long id) {
        if (!syllabusService.getSyllabusById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        syllabusService.deleteSyllabus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener planes de estudio por curso
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Syllabus>> getSyllabiByCourse(@PathVariable Long courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Syllabus> syllabi = syllabusService.getSyllabiByCourse(course.get());
        return new ResponseEntity<>(syllabi, HttpStatus.OK);
    }

    // Obtener planes de estudio por estado de aprobación
    @GetMapping("/approved/{status}")
    public ResponseEntity<List<Syllabus>> getSyllabiByApprovalStatus(@PathVariable boolean status) {
        List<Syllabus> syllabi = syllabusService.getSyllabiByApprovalStatus(status);
        return new ResponseEntity<>(syllabi, HttpStatus.OK);
    }

    // Obtener planes de estudio por aprobador
    @GetMapping("/approved-by/{userId}")
    public ResponseEntity<List<Syllabus>> getSyllabiByApprovedBy(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Syllabus> syllabi = syllabusService.getSyllabiByApprovedBy(user.get());
        return new ResponseEntity<>(syllabi, HttpStatus.OK);
    }

    // Aprobar un plan de estudio
    @PutMapping("/{id}/approve/{userId}")
    public ResponseEntity<Syllabus> approveSyllabus(@PathVariable Long id, @PathVariable Long userId) {
        Optional<User> approver = userService.getUserById(userId);
        if (approver.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Syllabus approvedSyllabus = syllabusService.approveSyllabus(id, approver.get());
        if (approvedSyllabus != null) {
            return new ResponseEntity<>(approvedSyllabus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}