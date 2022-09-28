package com.t95.t95backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.t95.t95backend.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	List<Portfolio> findByUserId(Long userId);

	Portfolio findByNameAndUserId(String name, Long userId);

	Portfolio findByUserIdAndName(Long userId, String name);

}
