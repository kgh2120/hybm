package com.dragontrain.md.domain.notice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.controller.response.HasnewNoticeResponse;
import com.dragontrain.md.domain.user.domain.User;

public interface NoticeService {
	AllNoticeResponse findAllNotDeletedNotice(User user, Pageable pageable);

	HasnewNoticeResponse existsNewNotice(User user);

	void deleteNotice(User user, Long noticeId);

	void deleteAllNotice(User user);

	void saveNotices(List<Food> foods);

	void saveFCMToken(User user, String token);

	void deleteFood(User user, String status, Long[] noticeId);

}
