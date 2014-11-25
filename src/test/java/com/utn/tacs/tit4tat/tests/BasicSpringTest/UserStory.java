package com.utn.tacs.tit4tat.tests.BasicSpringTest;

/**
* Class UserStory
* Description: An example bean that models a user story.
* Uses the standard story form:
* "As a ______" (user role), "I want to ______" (actionDesired)
* "so that _____" (outcomeDesired).
*/
public class UserStory {
private TestUser user;
private String actionDesired = "";
private String outcomeDesired = "";
public UserStory() {
}

public TestUser getUser() {
return user;
}

public void setUser(TestUser user) {
this.user = user;
}

public String getActionDesired() {
return actionDesired;
}

public void setActionDesired(String actionDesired) {
this.actionDesired = actionDesired;
}

public String getOutcomeDesired() {
return outcomeDesired;
}

public void setOutcomeDesired(String outcomeDesired) {
this.outcomeDesired = outcomeDesired;
}
}
