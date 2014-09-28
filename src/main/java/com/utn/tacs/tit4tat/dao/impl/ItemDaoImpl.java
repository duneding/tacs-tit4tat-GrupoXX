package com.utn.tacs.tit4tat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.model.Item;

@Service("itemDao")
@Scope("singleton")
public class ItemDaoImpl extends GenericDaoImpl<Item, Long> implements ItemDao {

	@Override
	public List<Item> getItemsByUser(Long userId) {
		List<Item> items = new ArrayList<Item>();
		
		//TODO implementar getItemsByUser()
		
		return items;
	}

}
