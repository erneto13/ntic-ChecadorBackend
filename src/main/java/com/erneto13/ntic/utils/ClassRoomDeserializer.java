package com.erneto13.ntic.utils;

import com.erneto13.ntic.model.ClassRoom;
import com.erneto13.ntic.repository.ClassRoomRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClassRoomDeserializer extends JsonDeserializer<ClassRoom> {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public ClassRoom deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.isExpectedStartObjectToken()) {
            return ctxt.readValue(p, ClassRoom.class);
        } else {
            int classRoomId = p.getValueAsInt();
            return classRoomRepository.findById((long) classRoomId)
                    .orElseThrow(() -> new RuntimeException("ClassRoom no encontrado con ID: " + classRoomId));
        }
    }
}