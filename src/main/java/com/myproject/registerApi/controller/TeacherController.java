package com.myproject.registerApi.controller;

import com.myproject.registerApi.model.TeacherDTO;
import com.myproject.registerApi.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TeacherController implements TeacherApi {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @Override
    public ResponseEntity<TeacherDTO> saveTeacher(TeacherDTO teacherDTO) {
        return ResponseEntity
                .status(201)
                .body(teacherService.saveTeacher(teacherDTO));
    }

    @Override
    public ResponseEntity<TeacherDTO> getTeacherById(UUID id) {
        return ResponseEntity
                .ok()
                .body(teacherService.getTeacher(id));
    }

    @Override
    public ResponseEntity<Void> deleteTeacherById(UUID id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }
}
