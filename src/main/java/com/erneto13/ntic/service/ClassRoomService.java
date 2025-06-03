package com.erneto13.ntic.service;

import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    public List<ClassRoom> getAllClassRooms() {
        return classRoomRepository.findAll();
    }

    public ClassRoom updateClassRoom(Long id, ClassRoom updatedClassRoom) {
        Optional<ClassRoom> existingClassRoomOpt = classRoomRepository.findById(id);

        if (existingClassRoomOpt.isPresent()) {
            ClassRoom existingClassRoom = existingClassRoomOpt.get();
            existingClassRoom.setName(updatedClassRoom.getName());
            existingClassRoom.setDescription(updatedClassRoom.getDescription());
            return classRoomRepository.save(existingClassRoom);
        }
        return null;
    }

    public Optional<ClassRoom> getClassRoomById(Long id) {
        return classRoomRepository.findById(id);
    }

    public ClassRoom saveClassRoom(ClassRoom classRoom) {
        return classRoomRepository.save(classRoom);
    }

    public void deleteClassRoom(Long id) {
        classRoomRepository.deleteById(id);
    }
}