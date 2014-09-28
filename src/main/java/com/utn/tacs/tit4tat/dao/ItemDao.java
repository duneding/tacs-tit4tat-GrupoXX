package com.utn.tacs.tit4tat.dao;

import java.util.List;

import com.utn.tacs.tit4tat.model.Item;

public interface ItemDao extends GenericDao<Item, Long> {
	
	public List<Item> getItemsByUser(Long userId);

}
