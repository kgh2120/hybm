package com.dragontrain.md.domain.notice.controller;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.controller.response.HasnewNoticeResponse;
import com.dragontrain.md.domain.notice.service.NoticeService;
import com.dragontrain.md.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping
	public ResponseEntity<AllNoticeResponse> findAllNotice(@AuthenticationPrincipal User user, @NotNull Pageable pageable){
		return ResponseEntity.ok(noticeService.findAllNotDeletedNotice(user, pageable));
	}

	@GetMapping("/hasnew")
	public ResponseEntity<HasnewNoticeResponse> existsNewNotice(@AuthenticationPrincipal User user){
		return ResponseEntity.ok(noticeService.existsNewNotice(user));
	}

}
