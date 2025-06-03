package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Group;
import com.erneto13.ntic.model.Group;
import com.erneto13.ntic.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group updateGroup(Long id, Group updatedGroup) {
        Optional<Group> existingGroupOpt = groupRepository.findById(id);

        if (existingGroupOpt.isPresent()) {
            Group existingGroup = existingGroupOpt.get();
            existingGroup.setName(updatedGroup.getName());
            existingGroup.setCareer(updatedGroup.getCareer());
            existingGroup.setHeadStudent(updatedGroup.getHeadStudent());

            return groupRepository.save(existingGroup);
        }
        return null;
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Group saveGroup(Group Group) {
        return groupRepository.save(Group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
