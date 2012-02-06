/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */

package cn.esup.dao.datadictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.utils.encode.JsonBinder;

import cn.esup.entity.datadictionary.DataDictionary;

/**
 * 数据字典表 Dao
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Repository
public class DataDictionaryDao extends HibernateDao<DataDictionary, Long> {

	/** 通过父级对象主键查询下属对象列表 */
	@SuppressWarnings("unchecked")
	public List<DataDictionary> queryDataDictionaryTreeView(Long parentId) {
		Criteria criteria = getSession().createCriteria(DataDictionary.class);
		if (parentId != null) {
			criteria.add(Restrictions.eq("parentId", parentId));
		} else {
			criteria.add(Restrictions.isNull("parentId"));
		}
		criteria.addOrder(Order.asc("sequNum"));// 按排序号升序排列查询结果
		return criteria.list();
	}

	/** 将主表对象主键作为字表对象的分页查询条件（Long typeId）*/
	@SuppressWarnings("unchecked")
	public Page<DataDictionary> queryDataDictionaryGridView(Long typeId, String query, Page<DataDictionary> page) {
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		if (typeId != null) {
			filters.add(new PropertyFilter("EQL_typeId", String.valueOf(typeId)));
		}
		if (query != null) {
			JsonBinder binder = JsonBinder.buildNonNullBinder();
			Map<String, String> map = binder.fromJson(query, Map.class);
			for (Entry<String, String> tmp : map.entrySet()) {
				if (tmp.getValue().length() > 0) {
					filters.add(new PropertyFilter(tmp.getKey(), tmp.getValue()));
				}
			}
		}
		return this.findPage(page, filters);
	}
}
