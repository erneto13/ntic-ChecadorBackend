package com.erneto13.ntic.utils;

import com.erneto13.ntic.model.Group;
import com.erneto13.ntic.repository.GroupRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GroupDeserializer extends JsonDeserializer<Group> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.isExpectedStartObjectToken()) {
            return ctxt.readValue(p, Group.class);
        } else {
            int groupId = p.getValueAsInt();
            return groupRepository.findById((long) groupId)
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + groupId));
        }
    }
}