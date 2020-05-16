package org.springframework.samples.controller;

import java.util.List;

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
	
	@RequestMapping("/index")
	public String performers(Model model) {
		List<PerformerInfo> performers = performerService.getPerformers();
		model.addAttribute("performers", performers);
		return "index";
	}

	@RequestMapping("/performer/detail")
	public String performerDetail(@RequestParam String email, Model model) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		model.addAttribute("performer", pi);
		return "performer/performerDetail";
	}
	
	@RequestMapping("/performer/delete")
	public String performerDelete(@RequestParam String email) {
		PerformerInfo pi = performerService.getPerformerInfoByEmail(email);
		if (pi == null) {
			return "performer/performerNotFound";
		}
		performerService.removePerformer(pi.getId());
		return "redirect:/index";
	}
}
