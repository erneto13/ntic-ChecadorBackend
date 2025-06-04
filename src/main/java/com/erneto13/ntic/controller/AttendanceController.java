package com.erneto13.ntic.controller;

import com.erneto13.ntic.dto.AttendanceDTO;
import com.erneto13.ntic.dto.ClassSessionDTO;
import com.erneto13.ntic.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Integer id) {
        AttendanceDTO attendanceDTO = attendanceService.getAttendanceById(Long.valueOf(id));
        return ResponseEntity.ok(attendanceDTO);
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO dto) {
        AttendanceDTO created = attendanceService.createAttendance(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(
            @PathVariable Integer id,
            @RequestBody AttendanceDTO dto) {
        AttendanceDTO updated = attendanceService.updateAttendance(Long.valueOf(id), dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Integer id) {
        attendanceService.deleteAttendance(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/verify/professor")
    public ResponseEntity<AttendanceDTO> verifyAttendanceByProfessor(@PathVariable Integer id) {
        AttendanceDTO verified = attendanceService.verifyAttendanceByProfessor(Long.valueOf(id));
        return ResponseEntity.ok(verified);
    }

    @PostMapping("/{id}/verify/head-student")
    public ResponseEntity<AttendanceDTO> verifyAttendanceByHeadStudent(@PathVariable Integer id) {
        AttendanceDTO verified = attendanceService.verifyAttendanceByHeadStudent(Long.valueOf(id));
        return ResponseEntity.ok(verified);
    }

    @PostMapping("/{id}/verify/checker")
    public ResponseEntity<AttendanceDTO> verifyAttendanceByChecker(
            @PathVariable Integer id,
            @RequestParam Long checkerId) {
        AttendanceDTO verified = attendanceService.verifyAttendanceByChecker(Long.valueOf(id), checkerId);
        return ResponseEntity.ok(verified);
    }
}