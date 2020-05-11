package org.springframework.samples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("login")
public class PerformerController {

	@Autowired
	private PerformerService performerService;

	@RequestMapping("/index")
	public String performers(Model model) {
		List<PerformerInfo> performers = performerService.getPerformers();
		model.addAttribute("performers", performers);
		return "index";
	}

	@RequestMapping("/performer/detail/{performerId}")
	public String performerDetail(@PathVariable int performerId, Model model) {
		PerformerInfo pi = performerService.getPerformerInfo(performerId);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		model.addAttribute("performer", pi);
		return "performer/performerDetail";
	}
	
	@RequestMapping("/performer/delete/{performerId}")
	public String performerDelete(@PathVariable int performerId, Model model) {
		PerformerInfo pi = performerService.getPerformerInfo(performerId);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		model.addAttribute("performer", pi);
		return "performer/performerDetail";
	}

	public void setMemberService(PerformerService memberService) {
		this.performerService = memberService;
	}

}
