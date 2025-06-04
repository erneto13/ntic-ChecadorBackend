package com.erneto13.ntic.dto;

import lombok.Data;

@Data
public class ClassSessionDTO {
    private Long id;
    private Long group;
    private Long subject;
    private Long professor;
    private Long classRoom;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}