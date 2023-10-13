package com.myproject.registerApi.service;


import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.TeacherDTO;
import com.myproject.registerApi.model.db.TeacherDb;
import com.myproject.registerApi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDTO saveTeacher(TeacherDTO teacher) {

        TeacherDb teacherdb = convertToDb(teacher);

        TeacherDb savedTeacher = teacherRepository.save(teacherdb);

        return convertToDTO(savedTeacher);
    }

    @Override
    public TeacherDTO getTeacher(UUID id) {
        TeacherDb teacherdb = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found id: " + id));

        return convertToDTO(teacherdb);
    }

    @Override
    public List<TeacherDTO> getAllTeacher() {

        return teacherRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTeacher(String id, TeacherDTO teacher) {
//        teacherRepository.
    }

    @Override
    public void deleteTeacher(String id) {
        teacherRepository.deleteById(UUID.fromString(id));
    }

    private TeacherDTO convertToDTO(TeacherDb teacherDb) {
        return new TeacherDTO()
                .id(teacherDb.getId())
                .firstName(teacherDb.getFirstName())
                .lastName(teacherDb.getLastName())
                .email(teacherDb.getEmail()
                );
    }

    private TeacherDb convertToDb(TeacherDTO teacherDto) {
        return new TeacherDb(
                teacherDto.getId(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getEmail()
        );
    }
}
