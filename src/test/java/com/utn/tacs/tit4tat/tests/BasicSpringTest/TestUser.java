package com.utn.tacs.tit4tat.tests.BasicSpringTest;

/**
 * Class User
 * Description: An example of a bean dependency. Story is dependent on Actor.
 */
public class TestUser {

	private String role;

	public TestUser() {
	setRole("User");
	}

	public String getRole() {
	return role;
	}

	public void setRole(String role) {
	this.role = role;
	}
}
