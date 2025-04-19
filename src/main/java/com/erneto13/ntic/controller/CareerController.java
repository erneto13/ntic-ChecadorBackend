package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Career;
import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/careers")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Career> getCareerById(@PathVariable Long id) {
        Career career = careerService.getCareerById(id);
        return ResponseEntity.ok(career);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Career> getCareerByName(@PathVariable String name) {
        Career career = careerService.getCareerByName(name);
        if (career == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(career);
    }

    @PostMapping
    public ResponseEntity<Career> createCareer(@RequestBody Career career) {
        Career newCareer = careerService.createCareer(career);
        return new ResponseEntity<>(newCareer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Career> updateCareer(@PathVariable Long id, @RequestBody Career career) {
        Career updatedCareer = careerService.updateCareer(id, career);
        return ResponseEntity.ok(updatedCareer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/subjects")
    public Set<Subject> getCareerSubjects(@PathVariable Long id) {
        return careerService.getCareerSubjects(id);
    }
}

