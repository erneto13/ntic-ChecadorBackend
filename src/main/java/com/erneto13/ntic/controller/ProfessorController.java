package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Obtener todos los profesores
    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> professors = professorService.getAllProfessors();
        return new ResponseEntity<>(professors, HttpStatus.OK);
    }

    // Obtener profesor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Optional<Professor> professor = professorService.getProfessorById(id);
        return professor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nuevo profesor
    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        Professor savedProfessor = professorService.saveProfessor(professor);
        return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
    }

    // Actualizar profesor existente
    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professor) {
        if (!professorService.getProfessorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professor.setId(Math.toIntExact(id));
        Professor updatedProfessor = professorService.saveProfessor(professor);
        return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
    }

    // Eliminar profesor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        if (!professorService.getProfessorById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professorService.deleteProfessor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener profesores por departamento
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Professor>> getProfessorsByDepartment(@PathVariable String department) {
        List<Professor> professors = professorService.getProfessorsByDepartment(department);
        return new ResponseEntity<>(professors, HttpStatus.OK);
    }
}