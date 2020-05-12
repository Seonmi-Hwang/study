package org.springframework.samples.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.samples.controller.PerformerForm;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.stereotype.Service;

@Service
public class PerformerService {

	private int nextMemberId = 1;
	private Map<Integer, PerformerInfo> performerMap = new HashMap<Integer, PerformerInfo>();

	public PerformerService() {

	}

	public List<PerformerInfo> getPerformers() {
		return new ArrayList<PerformerInfo>(performerMap.values());
	}

	public PerformerInfo getPerformerInfo(int performerId) {
		return performerMap.get(performerId);
	}

	public PerformerInfo getPerformerInfoByEmail(String email) {
		for (PerformerInfo pi : performerMap.values()) {
			if (pi.getEmail().equals(email))
				return pi;
		}
		return null;
	}

	public void removePerformer(int performerId) {
		performerMap.remove(performerId);
	}
	
	public int joinNewPerformer(PerformerForm performerForm) {
		PerformerInfo pi = new PerformerInfo(
							nextMemberId,
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
