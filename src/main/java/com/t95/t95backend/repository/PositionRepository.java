package com.t95.t95backend.repository;

import com.t95.t95backend.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long>  {
}
