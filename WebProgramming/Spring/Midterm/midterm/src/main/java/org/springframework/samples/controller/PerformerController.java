package org.springframework.samples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@RequestMapping("/performer/{performerId}")
	public String performerDetail(@PathVariable String performerId, Model model) {
		PerformerInfo pi = performerService.getPerformerInfo(performerId);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		model.addAttribute("member", pi);
		return "performer/performerDetail";
	}

	public void setMemberService(PerformerService memberService) {
		this.performerService = memberService;
	}

}
