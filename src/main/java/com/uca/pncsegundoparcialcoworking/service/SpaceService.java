package com.uca.pncsegundoparcialcoworking.service;

import com.uca.pncsegundoparcialcoworking.dto.SpaceRequest;
import com.uca.pncsegundoparcialcoworking.dto.SpaceResponse;

import java.util.List;

public interface SpaceService {
    SpaceResponse create(SpaceRequest request);
    List<SpaceResponse> listAll(String type, Boolean available);
    SpaceResponse getById(Long id);
    SpaceResponse update(Long id, SpaceRequest request);
    void delete(Long id);
}
