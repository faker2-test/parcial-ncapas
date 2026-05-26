package com.uca.pncsegundoparcialcoworking.controller;

import com.uca.pncsegundoparcialcoworking.dto.SpaceRequest;
import com.uca.pncsegundoparcialcoworking.dto.SpaceResponse;
import com.uca.pncsegundoparcialcoworking.service.SpaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceService service;

    public SpaceController(SpaceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SpaceResponse> create(@Valid @RequestBody SpaceRequest request) {
        SpaceResponse resp = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<SpaceResponse>> listAll(@RequestParam(required = false) String type,
                                                       @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(service.listAll(type, available));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceResponse> update(@PathVariable Long id, @Valid @RequestBody SpaceRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
