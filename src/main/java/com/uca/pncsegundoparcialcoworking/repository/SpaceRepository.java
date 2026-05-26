package com.uca.pncsegundoparcialcoworking.repository;

import com.uca.pncsegundoparcialcoworking.entity.Space;
import com.uca.pncsegundoparcialcoworking.entity.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    List<Space> findByType(SpaceType type);
    List<Space> findByAvailable(Boolean available);
    List<Space> findByTypeAndAvailable(SpaceType type, Boolean available);
}
