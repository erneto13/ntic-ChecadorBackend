package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        if (attendanceRepository.existsById(id)) {
            updatedAttendance.setId(Math.toIntExact(id));
            return attendanceRepository.save(updatedAttendance);
        }
        return null;
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return attendanceRepository.existsById(id);
    }
}