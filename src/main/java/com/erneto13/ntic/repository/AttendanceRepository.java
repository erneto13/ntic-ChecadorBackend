package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}