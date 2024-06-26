package com.dragontrain.md.domain.refrigerator.service;

import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.setting.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.request.ModifyAppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.request.ModifyAppliedStorageDesignRequest;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.exception.StorageDesignException;
import com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import com.dragontrain.md.domain.user.domain.User;

@ExtendWith(MockitoExtension.class)
class StorageStorageDesignServiceImplTest {

	@Mock
	private StorageStorageDesignRepository storageStorageDesignRepository;

	@Mock
	private RefrigeratorRepository refrigeratorRepository;

	@InjectMocks
	private StorageStorageDesignServiceImpl storageDesignService;

	private TestEntityFactory testEntityFactory = new TestEntityFactory();

	@Test
	void 모든_디자인_찾기() {
		User user = testEntityFactory.getTestUserEntity();
		Level level = testEntityFactory.getTestLevelEntity(1, 1);

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(storageStorageDesignRepository.findAllStorageDesign(any()))
			.willReturn(Collections.singletonList(testEntityFactory.getTestMyStorageDesignResponse(1, "냉장칸")));

		Assertions.assertDoesNotThrow(() -> storageDesignService.findAllStorageDesign(user));
		Assertions.assertEquals(storageDesignService.findAllStorageDesign(user).getCool().size(), 1);
	}

	@Test
	void 적용된_디자인_찾기() {
		User user = testEntityFactory.getTestUserEntity();
		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);

		AppliedStorageDesign ice = testEntityFactory.getTestAppliedStorageDesign(1, "1", StorageTypeId.ICE);
		AppliedStorageDesign cool = testEntityFactory.getTestAppliedStorageDesign(2, "2", StorageTypeId.COOL);
		AppliedStorageDesign cabinet = testEntityFactory.getTestAppliedStorageDesign(3, "3", StorageTypeId.CABINET);
		List<AppliedStorageDesign> designs = Arrays.asList(ice, cool, cabinet);

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(storageStorageDesignRepository.findAllAppliedStorageDesign(any()))
			.willReturn(designs);

		Assertions.assertEquals(storageDesignService.findAllAppliedStorageDesign(user).getIce().getId(), 1);
		Assertions.assertEquals(storageDesignService.findAllAppliedStorageDesign(user).getCool().getId(), 2);
		Assertions.assertEquals(storageDesignService.findAllAppliedStorageDesign(user).getCabinet().getId(), 3);
	}

	@Test
	void 중복포지션요청_실패() {
		User user = testEntityFactory.getTestUserEntity();
		List<ModifyAppliedStorageDesign> masds = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			masds.add(
				ModifyAppliedStorageDesign.builder()
					.designId(i)
					.position(StorageTypeId.COOL.name().toLowerCase())
					.build()
			);
		}

		ModifyAppliedStorageDesignRequest request = ModifyAppliedStorageDesignRequest.builder()
			.request(masds)
			.build();

		Assertions.assertThrows(StorageDesignException.class
			, () -> storageDesignService.modifyAppliedStorageDesign(user, request));
	}

	@Test
	void 냉장고찾기_실패() {
		User user = testEntityFactory.getTestUserEntity();

		List<ModifyAppliedStorageDesign> masds = new ArrayList<>();
		int designId = 1;
		for (StorageTypeId typeId : StorageTypeId.values()) {
			masds.add(
				ModifyAppliedStorageDesign.builder()
					.designId(designId++)
					.position(typeId.name().toLowerCase())
					.build()
			);
		}

		ModifyAppliedStorageDesignRequest request = ModifyAppliedStorageDesignRequest.builder()
			.request(masds)
			.build();

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.empty());

		Assertions.assertThrows(RefrigeratorException.class,
			() -> storageDesignService.modifyAppliedStorageDesign(user, request));

	}

	@Test
	void 보유하지_않거나_없는_디자인_실패() {
		User user = testEntityFactory.getTestUserEntity();

		List<ModifyAppliedStorageDesign> masds = new ArrayList<>();
		int designId = 1;
		for (StorageTypeId typeId : StorageTypeId.values()) {
			masds.add(
				ModifyAppliedStorageDesign.builder()
					.designId(designId++)
					.position(typeId.name().toLowerCase())
					.build()
			);
		}

		ModifyAppliedStorageDesignRequest request = ModifyAppliedStorageDesignRequest.builder()
			.request(masds)
			.build();

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE,
			testEntityFactory.getTestLevelEntity(1, 1));

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(storageStorageDesignRepository.findAllSSDByRefrigeratorIdAndSDIds(any(), any()))
			.willReturn(new ArrayList<>());

		Assertions.assertThrows(StorageDesignException.class,
			() -> storageDesignService.modifyAppliedStorageDesign(user, request));
	}

	@Test
	void 디자인과_위치매핑_실패() {
		User user = testEntityFactory.getTestUserEntity();

		List<ModifyAppliedStorageDesign> masds = new ArrayList<>();
		int designId = 1;
		for (StorageTypeId typeId : StorageTypeId.values()) {
			masds.add(
				ModifyAppliedStorageDesign.builder()
					.designId(designId++)
					.position(typeId.name().toLowerCase())
					.build()
			);
		}

		ModifyAppliedStorageDesignRequest request = ModifyAppliedStorageDesignRequest.builder()
			.request(masds)
			.build();

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE,
			testEntityFactory.getTestLevelEntity(1, 1));

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		List<StorageStorageDesign> SSDs = new ArrayList<>();
		StorageDesign sdCOOL = testEntityFactory.getTestMineUseDesign(
			StorageType.builder().storageType(StorageTypeId.COOL).build());
		StorageDesign sdICE = testEntityFactory.getTestMineUseDesign(
			StorageType.builder().storageType(StorageTypeId.ICE).build());
		StorageDesign sdCABINET = testEntityFactory.getTestMineUseDesign(
			StorageType.builder().storageType(StorageTypeId.CABINET).build());

		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.COOL).build(), sdCOOL, refrigerator));
		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.ICE).build(), sdICE, refrigerator));
		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.CABINET).build(), sdCABINET, refrigerator));

		Assertions.assertThrows(StorageDesignException.class,
			() -> storageDesignService.modifyAppliedStorageDesign(user, request));
	}

	@Test
	void 디자인변경_성공() {
		User user = testEntityFactory.getTestUserEntity(1L);

		List<ModifyAppliedStorageDesign> masds = new ArrayList<>();
		int designId = 1;
		for (StorageTypeId typeId : StorageTypeId.values()) {
			masds.add(
				ModifyAppliedStorageDesign.builder()
					.designId(designId++)
					.position(typeId.name().toLowerCase())
					.build()
			);
		}

		ModifyAppliedStorageDesignRequest request = ModifyAppliedStorageDesignRequest.builder()
			.request(masds)
			.build();

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE,
			testEntityFactory.getTestLevelEntity(1, 1, 1));

		List<StorageStorageDesign> SSDs = new ArrayList<>();
		designId = 1;
		StorageDesign sdICE = testEntityFactory.getTestMineUseDesign(designId++,
			StorageType.builder().storageType(StorageTypeId.ICE).build());
		StorageDesign sdCOOL = testEntityFactory.getTestMineUseDesign(designId++,
			StorageType.builder().storageType(StorageTypeId.COOL).build());
		StorageDesign sdCABINET = testEntityFactory.getTestMineUseDesign(designId,
			StorageType.builder().storageType(StorageTypeId.CABINET).build());

		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.ICE).build(), sdICE, refrigerator));
		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.COOL).build(), sdCOOL, refrigerator));
		SSDs.add(testEntityFactory.getTestStorageStorageDesignApplied(
			StorageType.builder().storageType(StorageTypeId.CABINET).build(), sdCABINET, refrigerator));

		List<StorageStorageDesign> newDesigns = new ArrayList<>();
		designId = 1;
		for (StorageTypeId storageTypeId : StorageTypeId.values()) {
			newDesigns.add(
				testEntityFactory.getTestStorageStorageDesignApplied(
					StorageType.builder().storageType(storageTypeId).build(),
					testEntityFactory.getTestMineUseDesign(designId++,
						StorageType.builder().storageType(storageTypeId).build()),
					refrigerator
				)
			);
		}

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(storageStorageDesignRepository.findAllSSDByRefrigeratorIdAndIdApplied(any(), any()))
			.willReturn(newDesigns);

		BDDMockito.given(storageStorageDesignRepository.findAllSSDByRefrigeratorIdAndSDIds(any(), any()))
			.willReturn(SSDs);

		Assertions.assertDoesNotThrow(() -> storageDesignService.modifyAppliedStorageDesign(user, request));

	}

	private <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new HashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
