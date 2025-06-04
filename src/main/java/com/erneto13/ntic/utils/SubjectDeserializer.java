package com.erneto13.ntic.utils;

import com.erneto13.ntic.model.Subject;
import com.erneto13.ntic.repository.SubjectRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubjectDeserializer extends JsonDeserializer<Subject> {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.isExpectedStartObjectToken()) {
            return ctxt.readValue(p, Subject.class);
        } else {
            int subjectId = p.getValueAsInt();
            return subjectRepository.findById((long) subjectId)
                    .orElseThrow(() -> new RuntimeException("Subject no encontrado con ID: " + subjectId));
        }
    }
}