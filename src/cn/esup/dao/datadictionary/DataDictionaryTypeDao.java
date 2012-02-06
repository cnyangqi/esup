/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */
package cn.esup.dao.datadictionary;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import cn.esup.entity.datadictionary.DataDictionaryType;

/**
 * 数据字典类型表 Dao
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Repository
public class DataDictionaryTypeDao extends HibernateDao<DataDictionaryType, Long> {

	/** 通过父级数据字典类型主键查询数据字典类型列表 */
	@SuppressWarnings("unchecked")
	public List<DataDictionaryType> queryDataDictionaryTypeTreeView(Long parentId) {
		Criteria criteria = getSession().createCriteria(DataDictionaryType.class);
		if (parentId != null) {
			criteria.add(Restrictions.eq("parentId", parentId));
		} else {
			criteria.add(Restrictions.isNull("parentId"));
		}
		criteria.addOrder(Order.asc("sequNum"));// 按排序号升序排列查询结果
		return criteria.list();
	}

}
