package org.springframework.samples.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerformerController {

	@Autowired
	private PerformerService performerService;
	
	@RequestMapping("/index") // performer 목록 출력 요청
	public String performers(Model model) {
		List<PerformerInfo> performers = performerService.getPerformers();
		model.addAttribute("performers", performers);
		return "index"; // index 화면 이동
	}

	@RequestMapping("/performer/detail") // performer 세부 정보 출력 요청
	public String performerDetail(@RequestParam String email, Model model) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound"; // performer를 찾을 수 없는 경우
		}
		model.addAttribute("performer", pi);
		return "performer/performerDetail"; // performerDetail form 이동
	}
	
	@RequestMapping("/performer/delete") // performer 삭제 요청
	public String performerDelete(@RequestParam String email,
			HttpSession session) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound"; // performer를 찾을 수 없는 경우
		}
		performerService.removePerformer(pi.getId()); // performer 삭제
		
		session.removeAttribute("login"); // login session 삭제
		session.invalidate(); // session과 session에 속해있는 값을 모두 삭제
		return "redirect:/index"; // index 화면 이동
	}
}
