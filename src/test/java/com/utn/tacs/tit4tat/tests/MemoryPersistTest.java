package com.utn.tacs.tit4tat.tests;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

public class MemoryPersistTest {

	@Test
	public void guardarEnMemoriaTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ItemService itemService = context.getBean("itemService",
				ItemService.class);

		Item item = new Item();
		item.setId(1L);
		item.setNombre("Ventilador");

		itemService.saveItem(item);

		for (Item unItem : itemService.getItems()) {
			System.out.println(unItem);
		}
	}

}
