package com.myproject.registerApi.controller;

import com.myproject.registerApi.model.ChildDTO;
import com.myproject.registerApi.service.ChildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ChildController implements ChildApi {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public ResponseEntity<ChildDTO> getChildById(UUID id) {

        return ResponseEntity
                .ok()
                .body(childService.getChild(id));
    }

    @Override
    public ResponseEntity<ChildDTO> saveChild(ChildDTO childDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(childService.saveChild(childDTO));
    }

    @Override
    public ResponseEntity<Void> deleteChildById(String id) {
        childService.deleteChild(id);
        return ResponseEntity.ok().build();
    }

}
