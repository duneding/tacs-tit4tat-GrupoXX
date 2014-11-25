package com.utn.tacs.tit4tat.tests.BasicSpringTest;

import org.junit.Test;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import static org.junit.Assert.*;

public class SpringTest {
	ApplicationContext ac;

	@Before
	public void setUp() {
		ac = new FileSystemXmlApplicationContext("file:src/main/resources/spring-config.xml");
	}
	
	@Test
	public void testUserCorrectFromPlainOldJUnitTest() {

	UserStory story = (UserStory) ac.getBean("userStory");
	// Spring is working fine using this app context
	assertEquals(story.getUser().getRole(), "SuperGenius User");

	// In this case our User is not wired up
	UserStory story2 = new UserStory();
	assertNull(story2.getUser());
	}

	@Test
	public void testPrewiredUserCorrect() {
	UserStory story = (UserStory) ac.getBean("userStory");
	assertEquals(story.getUser().getRole(), "SuperGenius User");
	}
}
