package com.erneto13.ntic.service;

import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.model.Syllabus;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.SyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SyllabusService {

    @Autowired
    private SyllabusRepository syllabusRepository;

    public List<Syllabus> getAllSyllabi() {
        return syllabusRepository.findAll();
    }

    public Optional<Syllabus> getSyllabusById(Long id) {
        return syllabusRepository.findById(id);
    }

    public List<Syllabus> getSyllabiByApprovalStatus(boolean approved) {
        return syllabusRepository.findByApproved(approved);
    }

    public List<Syllabus> getSyllabiByApprovedBy(User approvedBy) {
        return syllabusRepository.findByApprovedBy(approvedBy);
    }

    public Syllabus saveSyllabus(Syllabus syllabus) {
        return syllabusRepository.save(syllabus);
    }

    public void deleteSyllabus(Long id) {
        syllabusRepository.deleteById(id);
    }

    public Syllabus approveSyllabus(Long id, User approver) {
        Optional<Syllabus> syllabusOpt = syllabusRepository.findById(id);
        if (syllabusOpt.isPresent()) {
            Syllabus syllabus = syllabusOpt.get();
            syllabus.setApproved(true);
            syllabus.setApprovedBy(approver);
            return syllabusRepository.save(syllabus);
        }
        return null;
    }
}