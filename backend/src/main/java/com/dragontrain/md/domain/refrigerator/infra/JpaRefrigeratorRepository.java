package com.dragontrain.md.domain.refrigerator.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

public interface JpaRefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
}
