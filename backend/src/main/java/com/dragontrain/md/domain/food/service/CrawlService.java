package com.dragontrain.md.domain.food.service;

import java.util.Optional;

public interface CrawlService {

	Optional<BarcodeCreate> crawlBarcode(Long barcode);
}
