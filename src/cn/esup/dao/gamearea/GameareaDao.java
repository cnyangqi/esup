/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
 
package cn.esup.dao.gamearea;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.encode.JsonBinder;

import cn.esup.entity.account.Authority;
import cn.esup.entity.account.UserRole;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import cn.esup.entity.gamearea.Gamearea;

/**
 * ID Dao
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Repository
public class GameareaDao extends HibernateDao<Gamearea, Long>{
	
	/** 通过父级对象主键查询下属对象列表 */
	@SuppressWarnings("unchecked")
	public List<Gamearea> queryGameareaTreeView(Long parentId) {
		Criteria criteria = getSession().createCriteria(Gamearea.class);
		if (parentId != null) {
			criteria.add(Restrictions.eq("parentId", parentId));
		} else {
			criteria.add(Restrictions.isNull("parentId"));
		}
		criteria.addOrder(Order.asc("sequNum"));// 按排序号升序排列查询结果
		return criteria.list();
	}
	
	/** 单个对象分页查询条件 */
	@SuppressWarnings("unchecked")
	public Page<Gamearea> queryGameareaGridView(String query, Page<Gamearea> page) {
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
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
	
	/** 将主表对象主键作为子表对象的分页查询条件（Long typeId）*/
	@SuppressWarnings("unchecked")
	public Page<Gamearea> queryGameareaGridView(Long typeId, String query, Page<Gamearea> page) {
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
	
	/** SQL原生CRU */
	public void cru() {
		//	sql
		
		StringBuilder sb = new StringBuilder();
		sb.append("sql");

		Session session = getSession();
		session.createSQLQuery(sb.toString()).executeUpdate();
	}
	
	/** SQL原生Q分页 绑定hibernate持久化对象 */
	@SuppressWarnings("unchecked")
	public Page<Gamearea> queryGamearea(String query, Page<Gamearea> page){
		//	sql
		
		StringBuilder sb = new StringBuilder();
		sb.append("sql");
		
		if (query != null) {
			JsonBinder binder = JsonBinder.buildNonNullBinder();
			Map<String, String> map = binder.fromJson(query, Map.class);
			for (Entry<String, String> tmp : map.entrySet()) {
				if (tmp.getValue().length() > 0) {
					sb.append(" and ").append(tmp.getKey()).append(" like '%").append(tmp.getValue()).append("%'");
				}
			}
		}
		
		Session session = getSession();
		
		Long count = Long.parseLong(session.createSQLQuery(sb.toString()).uniqueResult().toString());
		page.setTotalCount(count);
		
		int start = sb.indexOf("count(t.role_id)");
		int end = start + "count(t.role_id)".length();
		sb = sb.replace(start, end, "SQL Column");// 需要查询的字段列表，以,分割

		int beginNum = (page.getPageNo() - 1) * page.getPageSize();// mysql分页起始行号计算，行号从0开始。
		sb.append(" limit ").append(beginNum).append(",").append(page.getPageSize());

		List<Gamearea> list = session.createSQLQuery(sb.toString()).addEntity(Gamearea.class).list();
		page.setResult(list);

		return page;
	}
	
	/** SQL原生Q分页 绑定非Hibernate持久化对象 */
	@SuppressWarnings("unchecked")
	public Page<Gamearea> queryNonPersistenceObject(String query, Page<Gamearea> page){
		//	sql
		
		StringBuilder sb = new StringBuilder();
		sb.append("sql");
		
		if (query != null) {
			JsonBinder binder = JsonBinder.buildNonNullBinder();
			Map<String, String> map = binder.fromJson(query, Map.class);
			for (Entry<String, String> tmp : map.entrySet()) {
				if (tmp.getValue().length() > 0) {
					sb.append(" and ").append(tmp.getKey()).append(" like '%").append(tmp.getValue()).append("%'");
				}
			}
		}
		
		Session session = getSession();
		
		Long count = Long.parseLong(session.createSQLQuery(sb.toString()).uniqueResult().toString());
		page.setTotalCount(count);
		
		int start = sb.indexOf("count(t.role_id)");
		int end = start + "count(t.role_id)".length();
		sb = sb.replace(start, end, "SQL Column");// 需要查询的字段列表，以,分割

		int beginNum = (page.getPageNo() - 1) * page.getPageSize();// mysql分页起始行号计算，行号从0开始。
		sb.append(" limit ").append(beginNum).append(",").append(page.getPageSize());

		SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.aliasToBean(Gamearea.class));// 需要绑定的非hibernate持久化对象
		sqlQuery.addScalar("id", new LongType());// 绑定的对象的属性以及属性类型

		page.setResult(sqlQuery.list());
		return page;
	}

}
