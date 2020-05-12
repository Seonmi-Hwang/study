package org.springframework.samples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class PerformerController {

	@Autowired
	private PerformerService performerService;
	
	@RequestMapping("/index")
	public String performers(Model model) {
		List<PerformerInfo> performers = performerService.getPerformers();
		model.addAttribute("performers", performers);
		return "index";
	}

	@RequestMapping("/performer/detail") // 쓸모없는 코드일 듯?
	public String performerDetail(@RequestParam String email, Model model) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		model.addAttribute("performer", pi);
		return "performer/performerDetail";
	}
	
	@RequestMapping("/performer/delete")
	public String performerDelete(@RequestParam String email, Model model) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		System.out.println("id : " + pi.getId());
		performerService.removePerformer(pi.getId());
		return "index";
	}

	public void setMemberService(PerformerService memberService) {
		this.performerService = memberService;
	}

}
