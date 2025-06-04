package com.erneto13.ntic.service;

import com.erneto13.ntic.dto.SubjectDTO;
import com.erneto13.ntic.model.Career;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.repository.CareerRepository;
import com.erneto13.ntic.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Transactional
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));
        return convertToDTO(subject);
    }

    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        Subject subject = convertToEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return convertToDTO(savedSubject);
    }

    @Transactional
    public SubjectDTO createSubjectWithCareer(SubjectDTO subjectDTO, Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setCareer(career);

        Subject savedSubject = subjectRepository.save(subject);
        return convertToDTO(savedSubject);
    }

    @Transactional
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));

        subject.setName(subjectDTO.getName());

        if (subjectDTO.getCareerId() != null) {
            Career career = careerRepository.findById(subjectDTO.getCareerId())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            subject.setCareer(career);
        }

        Subject updatedSubject = subjectRepository.save(subject);
        return convertToDTO(updatedSubject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));
        subjectRepository.delete(subject);
    }

    @Transactional
    public void assignSubjectToCareer(Long subjectId, Long careerId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + subjectId));
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        subject.setCareer(career);
        subjectRepository.save(subject);
    }

    @Transactional
    public Set<Professor> getSubjectProfessors(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + subjectId));
        return subject.getProfessors();
    }

    @Transactional
    public List<SubjectDTO> getSubjectsByCareer(Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        List<Subject> subjects = subjectRepository.findByCareer(career);
        return subjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para convertir Entity a DTO
    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());

        if (subject.getCareer() != null) {
            dto.setCareerId(subject.getCareer().getId());
            dto.setCareerName(subject.getCareer().getName());
        }

        if (subject.getProfessors() != null) {
            Set<Integer> professorIds = subject.getProfessors().stream()
                    .map(Professor::getId)
                    .collect(Collectors.toSet());
            dto.setProfessorIds(professorIds);
        }

        return dto;
    }

    private Subject convertToEntity(SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());

        if (dto.getCareerId() != null) {
            Career career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            subject.setCareer(career);
        }

        return subject;
    }
}