package com.dragontrain.md.domain.food.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.food.domain.Barcode;

public interface BarcodeRepository {

	Optional<Barcode> findByBarcodeId(Long barcodeId);

	void save(Barcode barcode);

}
