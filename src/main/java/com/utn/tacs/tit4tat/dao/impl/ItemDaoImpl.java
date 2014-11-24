package com.utn.tacs.tit4tat.dao.impl;

import static com.utn.tacs.tit4tat.objectify.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Usuario;

@Service("itemDao")
@Scope("singleton")
public class ItemDaoImpl extends GenericDaoImpl<Item, Long> implements ItemDao {

	@Override
	public List<Item> getItemsByUser(Usuario usuario) {
		List<Item> items = new ArrayList<Item>();
		
		items = (List<Item>) ofy().load().type(Item.class).filter("owner", usuario).list();
		return items;
	}
}
