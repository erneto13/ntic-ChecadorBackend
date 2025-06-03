package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
}