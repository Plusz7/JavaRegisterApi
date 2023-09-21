package com.myproject.registerApi.service;


import com.myproject.registerApi.model.TeacherDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 *  @author: DevProblems(Sarang Kumar A Tak)
 *  Youtube channel: https://youtube.com/@devproblems
 */
@Service
public class TeacherService {

    private final Map<UUID, TeacherDTO> db = new ConcurrentHashMap<>();

//    public TeacherDTO saveUser(TeacherDTO teacherDTO) {
//        UUID uuid = UUID.randomUUID();
//        userDTO.setId(uuid);
//        db.put(uuid, userDTO);
//        return userDTO;
//    }
//
//    public UserDTO getUser(UUID id) {
//        return Optional.ofNullable(db.get(id))
//                .orElseThrow(() -> new UserNotFoundException("User not found for Id: " + id));
//    }

}
