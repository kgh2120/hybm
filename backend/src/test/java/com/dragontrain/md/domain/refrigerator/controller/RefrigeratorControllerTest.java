package com.dragontrain.md.domain.refrigerator.controller;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.StorageStorageDesignService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = RefrigeratorController.class)
class RefrigeratorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private StorageStorageDesignService storageStorageDesignService;

	@InjectMocks
	private RefrigeratorController refrigeratorController;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@WithMockUser
	@Test
	void 전체디자인반환_성공() throws Exception{
		StorageDesignResponse storageDesignResponseCool = testEntityFactory.getTestMyStorageDesignResponse(1, 1, StorageTypeId.COOL);
		StorageDesignResponse storageDesignResponseICE = testEntityFactory.getTestMyStorageDesignResponse(2, 1, StorageTypeId.ICE);
		List<StorageDesignResponse> arr = Arrays.asList(storageDesignResponseCool, storageDesignResponseICE);

		BDDMockito.given(storageStorageDesignService.findAllStorageDesign(any()))
			.willReturn(StorageDesignsResponse.createByStorageType(arr));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/refrigerators/designs"))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
