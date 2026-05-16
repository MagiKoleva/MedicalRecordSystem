package com.project.medical_record_system.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + "with id " + id + " was not found!");
    }
}
