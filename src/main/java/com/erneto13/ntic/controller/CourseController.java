package com.erneto13.ntic.controller;

import com.erneto13.ntic.dto.CourseDto;
import com.erneto13.ntic.dto.ProfessorDto;
import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.service.CourseService;
import com.erneto13.ntic.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProfessorService professorService;

    // Obtener todos los cursos
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> courseDtos = courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    // Obtener curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nuevo curso
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        System.out.println("Course created: " + savedCourse);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    // Actualizar curso existente
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        if (updatedCourse != null) {
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.getCourseById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener cursos por profesor
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Course>> getCoursesByProfessor(@PathVariable Long professorId) {
        Optional<Professor> professor = professorService.getProfessorById(professorId);
        if (professor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Course> courses = courseService.getCoursesByProfessor(professor.get());
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // Obtener cursos por código de grupo
    @GetMapping("/group/{groupCode}")
    public ResponseEntity<List<Course>> getCoursesByGroupCode(@PathVariable String groupCode) {
        List<Course> courses = courseService.getCoursesByGroupCode(groupCode);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // auxiliary method to convert Course to CourseDto
    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setGroupCode(course.getGroupCode());
        dto.setDescription(course.getDescription());
        dto.setClassroom(course.getClassroom());

        if (course.getProfessor() != null) {
            ProfessorDto professorDto = new ProfessorDto();
            professorDto.setId(course.getProfessor().getId());
            professorDto.setName(course.getProfessor().getName());
            professorDto.setEmail(course.getProfessor().getEmail());
            professorDto.setSpecialty(course.getProfessor().getSpecialty());
            professorDto.setDepartment(course.getProfessor().getDepartment());
            professorDto.setRoleName(String.valueOf(course.getProfessor().getRole().getName()));

            dto.setProfessor(professorDto);
        }

        return dto;
    }
}