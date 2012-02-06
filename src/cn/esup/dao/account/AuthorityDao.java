package cn.esup.dao.account;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import cn.esup.entity.account.Authority;

/**
 * 授权对象的.
 * 
 * @author calvin
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {

	/** 删除角色与权限关联对象 */
	public void delete(Long roleId, Long authrityId) {
		// delete from acct_role_authority where 1=1 and role_id='1' and authority_id='9';// 删除时候表名不能取别名

		StringBuilder sb = new StringBuilder();
		sb.append("delete from acct_role_authority where 1=1");

		if (roleId != null) {
			sb.append(" and role_id='").append(roleId).append("'");
		}

		if (authrityId != null) {
			sb.append(" and authority_id='").append(authrityId).append("'");
		}

		Session session = getSession();
		session.createSQLQuery(sb.toString()).executeUpdate();
	}

}
