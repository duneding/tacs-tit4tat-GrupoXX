package com.utn.tacs.tit4tat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Service(value = "itemService")
@Scope(value = "prototype")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Override
	public Item saveItem(Item item) {
		Long id = this.itemDao.save(item);
		item.setId(id);
		return item;
	}

	@Override
	public void deleteItem(Item item) {
		itemDao.delete(item);
	}

	@Override
	public List<Item> getItems() {
		return this.itemDao.findAll();
	}

	@Override
	public Item getItemsById(Long id) {
		return this.itemDao.getById(id);
	}

	@Override
	public void updateItem(Item item) {
		this.itemDao.saveOrUpdate(item);
	}

	@Override
	public List<Item> getItemsByUser(Long userId) {
		return this.itemDao.getItemsByUser(userId);
	}
}
