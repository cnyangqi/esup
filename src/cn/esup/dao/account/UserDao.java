package cn.esup.dao.account;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.utils.encode.JsonBinder;

import cn.esup.entity.account.Authority;
import cn.esup.entity.account.Role;
import cn.esup.entity.account.User;
import cn.esup.entity.account.UserRole;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class UserDao extends HibernateDao<User, Long> {

	/** 将主表对象主键作为子表对象的分页查询条件（Long typeId）*/
	@SuppressWarnings("unchecked")
	public Page<User> queryUserGridView(Long typeId, String query, Page<User> page) {
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

	/** 查询角色树视图 */
	@SuppressWarnings("unchecked")
	public List<Role> queryRoleTreeView() {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.addOrder(Order.asc("id"));// 按排序号升序排列查询结果
		return criteria.list();
	}

	/** 通过角色id查询角色下面所有的权限列表视图 */
	@SuppressWarnings("unchecked")
	public Page<Authority> queryAuthGridView(Long roleId, String query, Page<Authority> page) {
		//	select count(t.id) from acct_authority t 
		//	where t.id in 
		//	(select t.authority_id from acct_role_authority t where 1=1 and t.role_id='1')
		//	limit 0,2;

		Session session = getSession();
		StringBuilder sb = new StringBuilder();

		sb.append("select count(t.id) from acct_authority t where t.id in (");
		sb.append("select t.authority_id from acct_role_authority t where 1=1 ");

		if (roleId != null) {
			sb.append("and  t.role_id='").append(roleId).append("'");
		}

		if (query != null) {
			JsonBinder binder = JsonBinder.buildNonNullBinder();
			Map<String, String> map = binder.fromJson(query, Map.class);
			for (Entry<String, String> tmp : map.entrySet()) {
				if (tmp.getValue().length() > 0) {
					sb.append(" and ").append(tmp.getKey()).append(" like '%").append(tmp.getValue()).append("%'");
				}
			}
		}

		sb.append(")");

		Long count = Long.parseLong(session.createSQLQuery(sb.toString()).uniqueResult().toString());
		page.setTotalCount(count);

		int start = sb.indexOf("count(t.id)");
		int end = start + "count(t.id)".length();
		sb = sb.replace(start, end, "*");

		int beginNum = (page.getPageNo() - 1) * page.getPageSize();// mysql分页起始行号计算，行号从0开始。
		sb.append(" limit ").append(beginNum).append(",").append(page.getPageSize());

		List<Authority> list = session.createSQLQuery(sb.toString()).addEntity(Authority.class).list();
		page.setResult(list);

		return page;
	}

	/** 用户角色列表视图 */
	@SuppressWarnings("unchecked")
	public Page<UserRole> queryRoleGridView(String query, Page<UserRole> page) {
		//	select t.role_id,t.user_id,`user`.login_name,role.`name` as role_name from 
		//	acct_user_role t,acct_user user,acct_role role
		//	where t.user_id=`user`.id and t.role_id=role.id
		//	and `user`.login_name like '%a%'
		//	and role.`name` like '%户%'

		Session session = getSession();
		StringBuilder sb = new StringBuilder();

		sb.append("select count(t.role_id) from acct_user_role t,acct_user user,acct_role role where t.user_id=`user`.id and t.role_id=role.id");

		if (query != null) {
			JsonBinder binder = JsonBinder.buildNonNullBinder();
			Map<String, String> map = binder.fromJson(query, Map.class);
			for (Entry<String, String> tmp : map.entrySet()) {
				if (tmp.getValue().length() > 0) {
					sb.append(" and ").append(tmp.getKey()).append(" like '%").append(tmp.getValue()).append("%'");
				}
			}
		}

		Long count = Long.parseLong(session.createSQLQuery(sb.toString()).uniqueResult().toString());
		page.setTotalCount(count);

		int start = sb.indexOf("count(t.role_id)");
		int end = start + "count(t.role_id)".length();
		sb = sb.replace(start, end,
				"t.role_id as roleId,t.user_id as userId,`user`.login_name as loginName,role.`name` as roleName");

		int beginNum = (page.getPageNo() - 1) * page.getPageSize();// mysql分页起始行号计算，行号从0开始。
		sb.append(" limit ").append(beginNum).append(",").append(page.getPageSize());

		//	List<UserRole> list = session.createSQLQuery(sb.toString()).addEntity(UserRole.class).list();

		SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sb.toString()).setResultTransformer(
				Transformers.aliasToBean(UserRole.class));
		sqlQuery.addScalar("roleId", new LongType());
		sqlQuery.addScalar("userId", new LongType());
		sqlQuery.addScalar("loginName", new StringType());
		sqlQuery.addScalar("roleName", new StringType());

		page.setResult(sqlQuery.list());
		return page;
	}

}
