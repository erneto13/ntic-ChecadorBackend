package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Syllabus;
import com.erneto13.ntic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    List<Syllabus> findByCourse(Course course);
    List<Syllabus> findByApproved(boolean approved);
    List<Syllabus> findByApprovedBy(User approvedBy);
}