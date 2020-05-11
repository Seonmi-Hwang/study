package org.springframework.samples.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.samples.controller.PerformerModRequest;
import org.springframework.samples.controller.PerformerNotFoundException;
import org.springframework.samples.controller.PerformerForm;
import org.springframework.samples.controller.NotMatchPasswordException;
import org.springframework.samples.model.Address;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.stereotype.Service;

@Service
public class PerformerService {

	private int nextMemberId = 0;
	private Map<String, PerformerInfo> performerMap = new HashMap<String, PerformerInfo>();

	public PerformerService() {

	}

	public List<PerformerInfo> getPerformers() {
		return new ArrayList<PerformerInfo>(performerMap.values());
	}

	public PerformerInfo getPerformerInfo(String performerId) {
		return performerMap.get(performerId);
	}

	public PerformerInfo getPerformerInfoByEmail(String email) {
		for (PerformerInfo pi : performerMap.values()) {
			if (pi.getEmail().equals(email))
				return pi;
		}
		return null;
	}
	
	public String joinNewPerformer(PerformerForm performerForm) {
		PerformerInfo pi = new PerformerInfo(
							"m" + nextMemberId,
							performerForm.getName(),
							performerForm.getEmail(), 
							performerForm.getPassword(),
							performerForm.getPhoneNumber(), 
							performerForm.getAddress(),
							performerForm.getType(),
							performerForm.getTitle(),
							performerForm.getTime(),
							performerForm.isFirst());
		nextMemberId++;
		performerMap.put(pi.getId(), pi);
		return pi.getId();
	}

	public void modifyPerformerInfo(PerformerModRequest modReq) {
		PerformerInfo pi = performerMap.get(modReq.getId());
		if (pi == null)
			throw new PerformerNotFoundException();
		if (!pi.matchPassword(modReq.getCurrentPassword()))
			throw new NotMatchPasswordException();

		pi.setEmail(modReq.getEmail());
		pi.setName(modReq.getName());
		pi.setPhoneNumber(modReq.getPhoneNumber());
		pi.setAddress(modReq.getAddress());
		pi.setType(modReq.getType());
		pi.setTitle(modReq.getTitle());
		pi.setTime(modReq.getTime());
		pi.setFirst(modReq.isFirst());
	}
}
