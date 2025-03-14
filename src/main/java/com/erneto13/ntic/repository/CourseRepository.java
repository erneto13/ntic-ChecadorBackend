package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProfessor(Professor professor);
    List<Course> findByGroupCode(String groupCode);
}