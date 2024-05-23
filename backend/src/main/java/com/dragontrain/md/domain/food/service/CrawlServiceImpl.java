package com.dragontrain.md.domain.food.service;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dragontrain.md.common.config.properties.CrawlProperties;
import com.dragontrain.md.common.config.exception.InternalServerCaughtException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlServiceImpl implements CrawlService {

	private final CrawlProperties crawlProperties;

	@Override
	public Optional<BarcodeCreate> crawlBarcode(Long barcode) {
		String url = crawlProperties.getBaseUrl() + barcode;
		try {
			Document document = Jsoup.connect(url).get();
			String productName = document.select(crawlProperties.getProductNameSelector()).text();
			if (!StringUtils.hasText(productName)) {
				return Optional.empty();
			}
			Integer kanCode = Integer.parseInt(document.select(crawlProperties.getKanCodeNameSelector()).get(0).text());
			log.info("crwal barcode data -> barcode : {}, name : {}, kancode : {}", barcode, productName, kanCode);
			return Optional.of(BarcodeCreate.create(barcode, productName, kanCode));
		} catch (IOException e) {
			throw new InternalServerCaughtException(e, this);
		}
	}
}
