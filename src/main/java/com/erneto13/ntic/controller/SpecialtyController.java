package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Specialty;
import com.erneto13.ntic.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    // CRUD para especialidades
    @GetMapping
    public List<Specialty> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Long id) {
        Specialty specialty = specialtyService.getSpecialtyById(id);
        return ResponseEntity.ok(specialty);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Specialty> getSpecialtyByName(@PathVariable String name) {
        Specialty specialty = specialtyService.getSpecialtyByName(name);
        if (specialty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specialty);
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty newSpecialty = specialtyService.createSpecialty(specialty);
        return new ResponseEntity<>(newSpecialty, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable Long id, @RequestBody Specialty specialty) {
        Specialty updatedSpecialty = specialtyService.updateSpecialty(id, specialty);
        return ResponseEntity.ok(updatedSpecialty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/professors")
    public Set<Professor> getSpecialtyProfessors(@PathVariable Long id) {
        return specialtyService.getSpecialtyProfessors(id);
    }
}
