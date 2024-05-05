package com.dragontrain.md.domain.notice.controller;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

	@GetMapping
	public ResponseEntity<AllNoticeResponse> findAllNotice(
		@AuthenticationPrincipal User user,
		Pageable pageable
		){
		return null;
	}

}
