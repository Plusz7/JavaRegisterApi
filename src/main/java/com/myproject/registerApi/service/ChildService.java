package com.myproject.registerApi.service;

import com.myproject.registerApi.model.ChildDTO;

import java.util.List;
import java.util.UUID;

public interface ChildService {

    ChildDTO saveChild(ChildDTO child);

    ChildDTO getChild(UUID id);

    List<ChildDTO> getAllChildren();

    void updateChild(String id, ChildDTO child);

    void deleteChild(String id);
}
