package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @GetMapping
    public ResponseEntity<List<ClassRoom>> getAllClassRooms() {
        return ResponseEntity.ok(classRoomService.getAllClassRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoom> getClassRoomById(@PathVariable Long id) {
        Optional<ClassRoom> classRoom = classRoomService.getClassRoomById(id);
        return classRoom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassRoom> createClassRoom(@RequestBody ClassRoom classRoom) {
        ClassRoom savedClassRoom = classRoomService.saveClassRoom(classRoom);
        return new ResponseEntity<>(savedClassRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Long id, @RequestBody ClassRoom classRoom) {
        ClassRoom updatedClassRoom = classRoomService.updateClassRoom(id, classRoom);
        return updatedClassRoom != null ?
                ResponseEntity.ok(updatedClassRoom) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }
}