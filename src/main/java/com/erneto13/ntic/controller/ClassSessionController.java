package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.ClassSession;
import com.erneto13.ntic.service.ClassSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class-sessions")
public class ClassSessionController {

    @Autowired
    private ClassSessionService classSessionService;

    @GetMapping
    public List<ClassSession> getAllClassSessions() {
        return classSessionService.getAllClassSessions();
    }

    @PostMapping
    public ResponseEntity<ClassSession> createClassSession(@RequestBody ClassSession classSession) {
        ClassSession newClassSession = classSessionService.createClassSession(classSession);
        return new ResponseEntity<>(newClassSession, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSession(@PathVariable Long id) {
        classSessionService.deleteClassSession(id);
        return ResponseEntity.noContent().build();
    }

}
