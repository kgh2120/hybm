package com.dragontrain.md.domain.food.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.Barcode;

public interface BarcodeJpaRepository extends JpaRepository<Barcode,Long> {
}
