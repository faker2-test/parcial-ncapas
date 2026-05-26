package com.uca.pncsegundoparcialcoworking.service.impl;

import com.uca.pncsegundoparcialcoworking.dto.SpaceRequest;
import com.uca.pncsegundoparcialcoworking.dto.SpaceResponse;
import com.uca.pncsegundoparcialcoworking.entity.Space;
import com.uca.pncsegundoparcialcoworking.entity.SpaceType;
import com.uca.pncsegundoparcialcoworking.exception.BusinessRuleException;
import com.uca.pncsegundoparcialcoworking.exception.ResourceNotFoundException;
import com.uca.pncsegundoparcialcoworking.repository.SpaceRepository;
import com.uca.pncsegundoparcialcoworking.service.SpaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository repository;

    public SpaceServiceImpl(SpaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public SpaceResponse create(SpaceRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessRuleException("Ya existe un espacio con ese nombre");
        }
        if (request.getPricePerHour() == null || request.getPricePerHour().doubleValue() <= 0) {
            throw new BusinessRuleException("El precio por hora debe ser mayor a 0");
        }
        Space s = toEntity(request);
        Space saved = repository.save(s);
        return toResponse(saved);
    }

    @Override
    public List<SpaceResponse> listAll(String type, Boolean available) {
        List<Space> list;
        if (type != null && available != null) {
            SpaceType t;
            try {
                t = SpaceType.valueOf(type);
            } catch (Exception ex) {
                throw new BusinessRuleException("Tipo inválido");
            }
            list = repository.findByTypeAndAvailable(t, available);
        } else if (type != null) {
            SpaceType t;
            try {
                t = SpaceType.valueOf(type);
            } catch (Exception ex) {
                throw new BusinessRuleException("Tipo inválido");
            }
            list = repository.findByType(t);
        } else if (available != null) {
            list = repository.findByAvailable(available);
        } else {
            list = repository.findAll();
        }
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public SpaceResponse getById(Long id) {
        Space s = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado"));
        return toResponse(s);
    }

    @Override
    public SpaceResponse update(Long id, SpaceRequest request) {
        Space existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado"));

        if (request.getName() != null && !request.getName().equalsIgnoreCase(existing.getName())) {
            if (repository.existsByNameIgnoreCase(request.getName())) {
                throw new BusinessRuleException("Ya existe un espacio con ese nombre");
            }
            existing.setName(request.getName());
        }

        if (request.getPricePerHour() != null) {
            if (request.getPricePerHour().doubleValue() <= 0) {
                throw new BusinessRuleException("El precio por hora debe ser mayor a 0");
            }
            existing.setPricePerHour(request.getPricePerHour());
        }

        if (request.getCapacity() != null) {
            if (request.getCapacity() < 1) throw new BusinessRuleException("La capacidad mínima es 1");
            existing.setCapacity(request.getCapacity());
        }

        if (request.getFloor() != null) {
            if (request.getFloor() < 0) throw new BusinessRuleException("El número de piso no puede ser negativo");
            existing.setFloor(request.getFloor());
        }

        if (request.getType() != null) existing.setType(request.getType());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getAmenities() != null) existing.setAmenities(request.getAmenities());
        if (request.getAvailable() != null) existing.setAvailable(request.getAvailable());

        Space saved = repository.save(existing);
        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Space existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado"));
        if (existing.getAvailable() != null && !existing.getAvailable()) {
            throw new BusinessRuleException("No se puede eliminar un espacio que no está disponible");
        }
        repository.delete(existing);
    }

    private Space toEntity(SpaceRequest r) {
        Space s = new Space();
        s.setName(r.getName());
        s.setDescription(r.getDescription());
        s.setType(r.getType());
        s.setCapacity(r.getCapacity());
        s.setPricePerHour(r.getPricePerHour());
        s.setAvailable(r.getAvailable() == null ? true : r.getAvailable());
        s.setFloor(r.getFloor());
        s.setAmenities(r.getAmenities());
        return s;
    }

    private SpaceResponse toResponse(Space s) {
        SpaceResponse r = new SpaceResponse();
        r.setId(s.getId());
        r.setName(s.getName());
        r.setDescription(s.getDescription());
        r.setType(s.getType());
        r.setCapacity(s.getCapacity());
        r.setPricePerHour(s.getPricePerHour());
        r.setAvailable(s.getAvailable());
        r.setFloor(s.getFloor());
        r.setAmenities(s.getAmenities());
        return r;
    }
}
