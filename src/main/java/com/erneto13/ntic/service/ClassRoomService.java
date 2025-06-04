package com.erneto13.ntic.service;

    import com.erneto13.ntic.dto.ClassRoomDTO;
    import com.erneto13.ntic.model.ClassRoom;
    import com.erneto13.ntic.repository.ClassRoomRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class ClassRoomService {

        @Autowired
        private ClassRoomRepository classRoomRepository;

        @Transactional
        public List<ClassRoomDTO> getAllClassRooms() {
            return classRoomRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Transactional
        public ClassRoomDTO getClassRoomById(Long id) {
            ClassRoom classRoom = classRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));
            return convertToDTO(classRoom);
        }

        @Transactional
        public ClassRoomDTO createClassRoom(ClassRoomDTO dto) {
            ClassRoom classRoom = new ClassRoom();
            classRoom.setName(dto.getName());
            classRoom.setDescription(dto.getDescription());

            ClassRoom savedClassRoom = classRoomRepository.save(classRoom);
            return convertToDTO(savedClassRoom);
        }

        @Transactional
        public ClassRoomDTO updateClassRoom(Long id, ClassRoomDTO dto) {
            ClassRoom classRoom = classRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));

            classRoom.setName(dto.getName());
            classRoom.setDescription(dto.getDescription());

            ClassRoom updatedClassRoom = classRoomRepository.save(classRoom);
            return convertToDTO(updatedClassRoom);
        }

        @Transactional
        public void deleteClassRoom(Long id) {
            ClassRoom classRoom = classRoomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));
            classRoomRepository.delete(classRoom);
        }

        private ClassRoomDTO convertToDTO(ClassRoom classRoom) {
            ClassRoomDTO dto = new ClassRoomDTO();
            dto.setId(classRoom.getId());
            dto.setName(classRoom.getName());
            dto.setDescription(classRoom.getDescription());
            return dto;
        }
    }