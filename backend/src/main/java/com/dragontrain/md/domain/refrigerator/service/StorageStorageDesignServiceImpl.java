package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.request.ModifyAppliedStorageDesignRequest;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import org.springframework.stereotype.Service;

import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.exception.StorageDesignErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.StorageDesignException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StorageStorageDesignServiceImpl implements StorageStorageDesignService {

	private final StorageStorageDesignRepository storageStorageDesignRepository;
	private final RefrigeratorRepository refrigeratorRepository;

	@Override
	public StorageDesignsResponse findAllStorageDesign(User user) {
		return StorageDesignsResponse.createByStorageType(
			storageStorageDesignRepository.findAllStorageDesign(
				refrigeratorRepository.findByUserId(user.getUserId())
					.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
					.getRefrigeratorId()
			)
		);
	}

	@Override
	public AppliedStorageDesignsResponse findAllAppliedStorageDesign(User user) {
		return AppliedStorageDesignsResponse.createByAppliedStorageDesign(
			storageStorageDesignRepository.findAllAppliedStorageDesign(
				refrigeratorRepository.findByUserId(user.getUserId())
					.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
					.getRefrigeratorId()
			)
		);
	}

	@Override
	public void modifyAppliedStorageDesign(User user, ModifyAppliedStorageDesignRequest request) {
		// request로 온 position이 이상한값이면 에러
		// 들어오면서 처리될것
		// 같은 position이 2개 이상이면 에러
		if(request.getRequest().stream().filter(distinctByKey(item -> item.getPosition())).toList().size() != 3) {
			throw new StorageDesignException(StorageDesignErrorCode.DUPLICATED_POSITION);
		}

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));
		// designId에 해당하는 디자인들을 불러오는데, 3개가 아니면 에러
		// -> 이때, 보유하지 않은 디자인인경우나 없는 디자인이면 3개가 아니니 동시에 처리가능
		List<StorageStorageDesign> newDesigns = storageStorageDesignRepository.findAllSSDByRefrigeratorIdAndSDIds(
			refrigerator.getRefrigeratorId()
			, request.getRequest().stream().map(item -> item.getDesignId()).toList()
		);

		if(newDesigns.size() != 3){
			throw new StorageDesignException(StorageDesignErrorCode.DESIGN_NOT_FOUND);
		}

		// 각 요청을 돌면서, 해당 designId과 position이 매칭되지 않으면 에러
		request.getRequest().forEach(item -> {
			if(!StorageTypeId.valueOf(item.getPosition().toUpperCase()).equals(
				newDesigns.stream().filter(design -> design.getStorageStorageDesignId().getStorageDesignId().equals(item.getDesignId()))
					.toList().get(0).getStorageType().getStorageType()
			)){
				throw new StorageDesignException(StorageDesignErrorCode.DESIGN_AND_POSITION_NOT_MATCHED);
			}
		});

		// 현재 적용중인 디자인들 불러오고 전부 false로 바꾼다.
		storageStorageDesignRepository.findAllSSDByRefrigeratorIdAndIdApplied(refrigerator.getRefrigeratorId(), Boolean.TRUE)
			.forEach(item -> item.dettach());
		// 새로 온 디자인들에 대해 true로 바꿔준다.
		newDesigns.forEach(item -> item.attach());
	}

	private <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new HashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
