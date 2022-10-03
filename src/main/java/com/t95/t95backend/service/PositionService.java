package com.t95.t95backend.service;

import com.t95.t95backend.entity.Position;
import com.t95.t95backend.repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }
}
