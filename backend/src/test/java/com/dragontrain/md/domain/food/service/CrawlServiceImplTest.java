package com.dragontrain.md.domain.food.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.common.config.properties.CrawlProperties;

@ExtendWith(MockitoExtension.class)
class CrawlServiceImplTest {

	@Mock
	CrawlProperties crawlProperties;

	@InjectMocks
	CrawlServiceImpl crawlService;

	@Test
	void crawlBarcodeSuccessTest() throws Exception {
		// given
		Long givenBarcode = 8801048941025L;
		given(crawlProperties.getBaseUrl()).willReturn("https://gs1.koreannet.or.kr/pr/");
		given(crawlProperties.getProductNameSelector()).willReturn(".pv_title > h3");
		given(crawlProperties.getKanCodeNameSelector()).willReturn(".pv_info > tbody > tr > td");
		// when
		Optional<BarcodeCreate> sut = crawlService.crawlBarcode(givenBarcode);
		// then
		assertThat(sut.isPresent()).isTrue();
	}

	@Test
	void crawlBarcodeFailTest() throws Exception {
		// given
		Long givenBarcode = 1313L;
		given(crawlProperties.getBaseUrl()).willReturn("https://gs1.koreannet.or.kr/pr/");
		given(crawlProperties.getProductNameSelector()).willReturn(".pv_title > h3");

		// when
		Optional<BarcodeCreate> sut = crawlService.crawlBarcode(givenBarcode);
		// then
		assertThat(sut.isEmpty()).isTrue();
	}

	@Test
	void jsoupTest() throws Exception {
		Long barcode = 8801048941025L;
		String url = "https://gs1.koreannet.or.kr/pr/" + barcode;

		Document document = Jsoup.connect(url).get();
		//when
		Elements productName = document.select(".pv_title > h3");
		String kanCode = document.select(".pv_info > tbody > tr > td").get(0).text();

		// then
		assertThat(productName.text()).isEqualTo("하이트진로(주) 참이슬 오리지널 360mL x 1EA");
		assertThat(kanCode).isEqualTo("01230101");
	}

	@Test
	void jsoupFailTest() throws Exception {
		Long barcode = 13131L;
		String url = "https://gs1.koreannet.or.kr/pr/" + barcode;

		System.out.println(url);
		Document document = Jsoup.connect(url).get();
		String productName = document.select(".pv_title > h3").text();
		String kanCode = document.select(".pv_info > tbody > tr > td").get(0).text();
		//when
		// System.out.println(document);
		System.out.println(productName);
		System.out.println(kanCode);
		assertThat(productName).isEmpty();
		assertThat(kanCode).isEmpty();
	}
}
