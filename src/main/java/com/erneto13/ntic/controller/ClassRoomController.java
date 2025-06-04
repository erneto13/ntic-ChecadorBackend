package com.erneto13.ntic.controller;

import com.erneto13.ntic.dto.ClassRoomDTO;
import com.erneto13.ntic.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @GetMapping
    public List<ClassRoomDTO> getAllClassRooms() {
        return classRoomService.getAllClassRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> getClassRoomById(@PathVariable Long id) {
        ClassRoomDTO classRoomDTO = classRoomService.getClassRoomById(id);
        return ResponseEntity.ok(classRoomDTO);
    }

    @PostMapping
    public ResponseEntity<ClassRoomDTO> createClassRoom(@RequestBody ClassRoomDTO dto) {
        ClassRoomDTO created = classRoomService.createClassRoom(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> updateClassRoom(@PathVariable Long id, @RequestBody ClassRoomDTO dto) {
        ClassRoomDTO updated = classRoomService.updateClassRoom(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }
}