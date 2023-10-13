package com.myproject.registerApi.service;

import com.myproject.registerApi.model.TeacherDTO;

import java.util.List;
import java.util.UUID;

public interface TeacherService {

    TeacherDTO saveTeacher(TeacherDTO teacher);

    TeacherDTO getTeacher(UUID id);

    List<TeacherDTO> getAllTeacher();

    void updateTeacher(String id, TeacherDTO teacher);

    void deleteTeacher(String id);
}
