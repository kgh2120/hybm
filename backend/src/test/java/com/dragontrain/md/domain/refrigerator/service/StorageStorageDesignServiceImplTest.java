package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import com.dragontrain.md.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StorageStorageDesignServiceImplTest {

	@Mock
	private StorageStorageDesignRepository storageDesignRepository;

	@Mock
	private RefrigeratorRepository refrigeratorRepository;

	@InjectMocks
	private StorageStorageDesignServiceImpl storageDesignService;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 모든_디자인_찾기(){
		User user = testEntityFactory.getTestUserEntity(null);
		Level level = testEntityFactory.getTestLevelEntity(null,1, 1);

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(null, user, Boolean.FALSE, level);

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(refrigerator);

		BDDMockito.given(storageDesignRepository.findAllStorageDesign(any()))
			.willReturn(Arrays.asList(testEntityFactory.getTestMyStorageDesignResponse(1, 1, "냉장칸")));

		Assertions.assertDoesNotThrow(() -> storageDesignService.findAllStorageDesign(user));
		Assertions.assertEquals(storageDesignService.findAllStorageDesign(user).getCool().size(), 1);
	}

}
