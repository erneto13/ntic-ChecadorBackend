package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.repository.CourseRepository;
import com.erneto13.ntic.utils.GroupCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupCodeGenerator groupCodeGenerator;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourseOpt = courseRepository.findById(id);

        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();

            updatedCourse.setId(Math.toIntExact(id));
            updatedCourse.setGroupCode(existingCourse.getGroupCode());

            return courseRepository.save(updatedCourse);
        }
        return null;
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByProfessor(Professor professor) {
        return courseRepository.findByProfessor(professor);
    }

    public List<Course> getCoursesByGroupCode(String groupCode) {
        return courseRepository.findByGroupCode(groupCode);
    }

    public Course saveCourse(Course course) {
        if (course.getId() == null || course.getGroupCode() == null || course.getGroupCode().isEmpty()) {
            String uniqueGroupCode = groupCodeGenerator.generateUniqueGroupCode();
            course.setGroupCode(uniqueGroupCode);
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}