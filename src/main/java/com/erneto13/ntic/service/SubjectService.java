package com.erneto13.ntic.service;

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

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Transactional
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));
    }

    @Transactional
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject createSubjectWithCareer(Subject subject, Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        subject.setCareer(career);
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = getSubjectById(id);
        subject.setName(subjectDetails.getName());
        if (subjectDetails.getCareer() != null) {
            subject.setCareer(subjectDetails.getCareer());
        }
        return subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
    }

    @Transactional
    public void assignSubjectToCareer(Long subjectId, Long careerId) {
        Subject subject = getSubjectById(subjectId);
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        subject.setCareer(career);
        subjectRepository.save(subject);
    }

    @Transactional
    public Set<Professor> getSubjectProfessors(Long subjectId) {
        Subject subject = getSubjectById(subjectId);
        return subject.getProfessors();
    }

    @Transactional
    public List<Subject> getSubjectsByCareer(Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        return subjectRepository.findByCareer(career);
    }
}
