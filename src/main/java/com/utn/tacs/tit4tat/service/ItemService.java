package com.utn.tacs.tit4tat.service;

import java.util.List;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Usuario;

public interface ItemService {
	
	public Item saveItem(Item item);

	public void deleteItem(Item item);

	public List<Item> getItems();
	
	public List<Item> getItemsByUser(Usuario usuario);

	public Item getItemsById(Long id);

	public void updateItem(Item item);
}
