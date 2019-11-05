package model.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.User;
import model.dao.UserDAO;

// an example business class
public class UserAnalysis {
	private UserDAO dao;
	
	public UserAnalysis() {}
	
	public UserAnalysis(UserDAO dao) {
		super();
		this.dao = dao;
	}

	// an example business method
	public List<User> recommendFriends(String userId) throws Exception {
		User thisuser = dao.findUser(userId);
		if (thisuser == null) {
			throw new Exception("invalid user ID!");
		}
		int m1 = userId.indexOf('@');
		if (m1 == -1) return null;
		String mserver1 = thisuser.getEmail().substring(m1);
		
		List<User> friends = new ArrayList<User>();
		
		List<User> userList = dao.findUserList(1, 10000);
		Iterator<User> userIter = userList.iterator();		
		while (userIter.hasNext()) {
			User user = (User)userIter.next();
			
			if (user.getUserId().equals(userId)) continue;
			int m2 = user.getEmail().indexOf('@');
			if (m2 == -1) continue;
			String mserver2 = user.getEmail().substring(m2);

			if (mserver1.equals(mserver2)) 
				friends.add(user);		
		}
		return friends;
	}

}
