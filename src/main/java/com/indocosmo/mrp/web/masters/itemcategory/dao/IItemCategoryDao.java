package com.indocosmo.mrp.web.masters.itemcategory.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.itemcategory.model.ItemCategory;

@Repository
@Qualifier("IItemCategoryDao")
public interface IItemCategoryDao extends IMasterBaseDao<ItemCategory>{

}
