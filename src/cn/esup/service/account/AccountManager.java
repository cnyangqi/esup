package cn.esup.service.account;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.esup.common.SS3Tools;
import cn.esup.dao.account.AuthorityDao;
import cn.esup.dao.account.RoleDao;
import cn.esup.dao.account.UserDao;
import cn.esup.entity.account.Authority;
import cn.esup.entity.account.Role;
import cn.esup.entity.account.User;
import cn.esup.entity.account.UserRole;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.service.ServiceException;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private UserDao userDao;
	private RoleDao roleDao;
	private AuthorityDao authorityDao;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	//-- Authority Manager --//
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	/** 查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryUserGridView(Long typeId, String query, int pageNo, int pageSize) {
		Page<User> page = new Page<User>(pageSize);
		page.setPageNo(pageNo);
		page = userDao.queryUserGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换用户状态 */
	public void toggleUserStatus(Long id) {
		User user = userDao.get(id);
		if (user.getStatus().equalsIgnoreCase("1")) {
			user.setStatus("0");
		} else {
			user.setStatus("1");
		}
		userDao.save(user);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteUser(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		userDao.batchExecute("delete User t where t.id in (:ids)", map);// 命名参数
	}

	/** 查询角色树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryRoleTreeView() {
		Iterator<Role> it = userDao.queryRoleTreeView().iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Role tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			node.setText(tmp.getName());
			list.add(node);
		}
		return list;
	}

	/** 通过角色id查询角色下面所有的权限列表视图 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryAuthGridView(Long roleId, String query, int pageNo, int pageSize) {
		Page<Authority> page = new Page<Authority>(pageSize);
		page.setPageNo(pageNo);
		page = userDao.queryAuthGridView(roleId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 用户角色列表视图 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryRoleGridView(String query, int pageNo, int pageSize) {

		Page<UserRole> page = new Page<UserRole>(pageSize);
		page.setPageNo(pageNo);
		page = userDao.queryRoleGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 关联角色保存权限  */
	public void saveAuth(Authority auth) {
		authorityDao.save(auth);

		Role role = roleDao.get(auth.getRoleId());
		List<Authority> list = role.getAuthorityList();
		list.add(auth);
		role.setAuthorityList(list);
		roleDao.save(role);

	}

	/** 通过权限主键查询权限 */
	public Authority getAuth(Long id) {
		return null;
	}

	/** 批量删除权限 */
	public void batchDeleteAuth(String ids) {
		String[] temp = ids.split(",");
		for (String s : temp) {
			Authority auth = authorityDao.get(Long.parseLong(s));
			authorityDao.delete(auth.getRoleId(), auth.getId());
		}

		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		authorityDao.batchExecute("delete Authority t where t.id in (:ids)", map);// 命名参数
	}

	/** 切换权限状态 */
	public void toggleAuthStatus(Long id) {
		Authority auth = authorityDao.get(id);
		if (auth.getStatus().equalsIgnoreCase("1")) {
			auth.setStatus("0");
		} else {
			auth.setStatus("1");
		}
		authorityDao.save(auth);
	}

	/** 给用户配置角色 */
	public void saveUserRole(String userId, Long roleId) {
		roleDao.saveUserRole(userId, roleId);
	}

	/** 删除用户角色 */
	public void deleteUserRole(String ids) {
		String[] temp = ids.split(",");
		roleDao.deleteUserRole(temp[0], temp[1]);
	}
}
