package com.dragontrain.md.domain.food.service;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dragontrain.md.common.config.CrawlProperties;
import com.dragontrain.md.common.config.exception.InternalServerCaughtException;

import lombok.RequiredArgsConstructor;

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
			return Optional.of(BarcodeCreate.create(productName, kanCode));
		} catch (IOException e) {
			throw new InternalServerCaughtException(e, this);
		}
	}
}
