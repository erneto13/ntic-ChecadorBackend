package com.erneto13.ntic.utils;

public class AttendanceLimitExceededException extends RuntimeException {
    public AttendanceLimitExceededException(String message) {
        super(message);
    }
}

