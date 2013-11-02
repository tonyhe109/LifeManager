package com.lifemanager.user.data;

public class UserPerfernce {

	private static final UserPerfernce _perfernce = new UserPerfernce();

	private UserPerfernce() {

	}

	public static UserPerfernce getUserPrefence() {
		return _perfernce;
	}
	
	
}
