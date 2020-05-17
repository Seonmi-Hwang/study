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

	private int nextPerformerId = 0;
	private Map<String, PerformerInfo> performerMap = new HashMap<String, PerformerInfo>();

	public PerformerService() {
		performerMap.put("p1", new PerformerInfo("p1", "도담", "dodami@dongduk.ac.kr", "123456", "010-1234-4567", new Address("한국", "서울", "01520"), PerformerType.Singer, "멍멍", "금", "20", false));
		nextPerformerId = 2;
	}

	public List<PerformerInfo> getPerformers() { // performer 목록을 hashMap에서 가져옴
		return new ArrayList<PerformerInfo>(performerMap.values());
	}

	public PerformerInfo getPerformerInfo(String performerId) { // performerId로 performer를 가져옴
		return performerMap.get(performerId);
	}

	public PerformerInfo getPerformerInfoByEmail(String email) { // email로 performer를 가져옴
		for (PerformerInfo pi : performerMap.values()) {
			if (pi.getEmail().equals(email))
				return pi;
		}
		return null;
	}

	public void removePerformer(String performerId) { // performerId로 performer 삭제
		performerMap.remove(performerId);
	}
	
	public String joinNewPerformer(PerformerForm performerForm) { // performerForm으로 새 performer 생성
		PerformerInfo pi = new PerformerInfo(
							"p" + nextPerformerId,
							performerForm.getName(),
							performerForm.getEmail(), 
							performerForm.getPassword(),
							performerForm.getPhoneNumber(), 
							performerForm.getAddress(),
							performerForm.getType(),
							performerForm.getTitle(),
							performerForm.getDay(),
							performerForm.getTime(),
							performerForm.isFirst());
		nextPerformerId++;
		performerMap.put(pi.getId(), pi);
		return pi.getId();
	}
	
}
