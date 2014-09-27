package com.utn.tacs.tit4tat.dao.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.utn.tacs.tit4tat.dao.ItemDao;
import com.utn.tacs.tit4tat.model.Item;

@Service("itemDao")
@Scope("singleton")
public class ItemDaoImpl extends GenericDaoImpl<Item, Long> implements ItemDao {

}
