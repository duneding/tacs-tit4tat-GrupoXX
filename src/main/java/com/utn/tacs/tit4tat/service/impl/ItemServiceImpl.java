package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Service(value="itemService")
@Scope(value="prototype")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public Item saveItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getItemsById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		
	}
}
