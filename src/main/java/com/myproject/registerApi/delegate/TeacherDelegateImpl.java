package com.myproject.registerApi.delegate;

import com.myproject.registerApi.controller.TeacherApiDelegate;
import com.myproject.registerApi.service.TeacherService;
import com.myproject.registerApi.model.TeacherDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
 *  @author: DevProblems(Sarang Kumar A Tak)
 *  Youtube channel: https://youtube.com/@devproblems
 */
@Service
public class TeacherDelegateImpl implements TeacherApiDelegate {

    private final TeacherService teacherService;

    public TeacherDelegateImpl(TeacherService userService) {
        this.teacherService = userService;
    }

}
