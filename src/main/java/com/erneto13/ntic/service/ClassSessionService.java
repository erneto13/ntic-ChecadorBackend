package com.erneto13.ntic.service;

import com.erneto13.ntic.dto.ClassSessionDTO;
import com.erneto13.ntic.model.ClassSession;
import com.erneto13.ntic.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassSessionService {

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Transactional
    public List<ClassSession> getAllClassSessions() {
        return classSessionRepository.findAll();
    }

    public ClassSessionDTO createClassSession(ClassSessionDTO dto) {
        ClassSession classSession = new ClassSession();
        classSession.setDayOfWeek(dto.getDayOfWeek());
        classSession.setStartTime(dto.getStartTime());
        classSession.setEndTime(dto.getEndTime());

        if (dto.getGroup() != null) {
            classSession.setGroup(groupRepository.findById(dto.getGroup())
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado")));
        }

        if (dto.getSubject() != null) {
            classSession.setSubject(subjectRepository.findById(dto.getSubject())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada")));
        }

        if (dto.getProfessor() != null) {
            classSession.setProfessor(professorRepository.findById(dto.getProfessor())
                    .orElseThrow(() -> new RuntimeException("Profesor no encontrado")));
        }

        if (dto.getClassRoom() != null) {
            classSession.setClassRoom(classRoomRepository.findById(dto.getClassRoom())
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada")));
        }

        ClassSession savedSession = classSessionRepository.save(classSession);

        return convertToDTO(savedSession);
    }

    private ClassSessionDTO convertToDTO(ClassSession session) {
        ClassSessionDTO dto = new ClassSessionDTO();
        dto.setId(Long.valueOf(session.getId()));
        dto.setDayOfWeek(session.getDayOfWeek());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());

        if (session.getGroup() != null) {
            dto.setGroup(Long.valueOf(session.getGroup().getId()));
        }

        if (session.getSubject() != null) {
            dto.setSubject(session.getSubject().getId());
        }

        if (session.getProfessor() != null) {
            dto.setProfessor(Long.valueOf(session.getProfessor().getId()));
        }

        if (session.getClassRoom() != null) {
            dto.setClassRoom(session.getClassRoom().getId());
        }

        return dto;
    }

    @Transactional
    public ClassSession getClassSessionById(Long id) {
        return classSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada con id: " + id));
    }

    @Transactional
    public void deleteClassSession(Long id) {
        ClassSession classSession = getClassSessionById(id);
        classSessionRepository.delete(classSession);
    }

    @Transactional
    public List<ClassSession> getClassSessionsByDayAndStartTime(String dayOfWeek, String startTime) {
        return classSessionRepository.findByDayOfWeekAndStartTime(dayOfWeek, startTime);
    }
}
