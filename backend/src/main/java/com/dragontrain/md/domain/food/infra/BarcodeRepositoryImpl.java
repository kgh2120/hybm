package com.dragontrain.md.domain.food.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.service.BarcodeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BarcodeRepositoryImpl implements BarcodeRepository {

	private final BarcodeJpaRepository barcodeJpaRepository;

	@Override
	public Optional<Barcode> findByBarcodeId(Long barcodeId) {
		return barcodeJpaRepository.findById(barcodeId);
	}

	@Override
	public void save(Barcode barcode) {
		barcodeJpaRepository.save(barcode);
	}
}
