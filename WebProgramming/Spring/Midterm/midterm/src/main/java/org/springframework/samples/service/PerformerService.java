package org.springframework.samples.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.samples.controller.PerformerForm;
import org.springframework.samples.model.Address;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.model.PerformerType;
import org.springframework.stereotype.Service;

@Service
public class PerformerService {

	private int nextMemberId = 0;
	private Map<String, PerformerInfo> performerMap = new HashMap<String, PerformerInfo>();

	public PerformerService() {
		performerMap.put("p1", new PerformerInfo("p1", "도담", "dodami@dongduk.ac.kr", "123456", "010-1234-4567", new Address("한국", "서울", "01520"), PerformerType.Singer, "멍멍", "20", false));
		nextMemberId = 2;
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

	public void removePerformer(String performerId) {
		performerMap.remove(performerId);
	}
	
	public String joinNewPerformer(PerformerForm performerForm) {
		PerformerInfo pi = new PerformerInfo(
							"p" + nextMemberId,
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
	
}
