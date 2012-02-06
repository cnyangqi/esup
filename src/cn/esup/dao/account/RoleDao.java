package cn.esup.dao.account;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import cn.esup.entity.account.Role;
import cn.esup.entity.account.User;

/**
 * 角色对象的泛型DAO
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
@Component
public class RoleDao extends HibernateDao<Role, Long> {

	private static final String QUERY_USER_BY_ROLEID = "select u from User u left join u.roleList r where r.id=?";

	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Role role = get(id);
		//查询出拥有该角色的用户,并删除该用户的角色.
		List<User> users = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (User u : users) {
			u.getRoleList().remove(role);
		}
		super.delete(role);
	}

	/** 给用户配置角色 */
	public void saveUserRole(String userId, Long roleId) {
		//	insert into acct_user_role(user_id,role_id) values(2,3);

		StringBuilder sb = new StringBuilder();
		sb.append("insert into acct_user_role(user_id,role_id) values(");
		sb.append(userId).append(",");
		sb.append(roleId);
		sb.append(")");

		Session session = getSession();
		session.createSQLQuery(sb.toString()).executeUpdate();
	}

	/** 删除用户角色 */
	public void deleteUserRole(String userId, String roleId) {
		//	delete from acct_user_role where user_id= and role_id=
		StringBuilder sb = new StringBuilder();
		sb.append("delete from acct_user_role where user_id=");
		sb.append(userId).append(" and role_id= ");
		sb.append(roleId);

		Session session = getSession();
		session.createSQLQuery(sb.toString()).executeUpdate();
	}
}
