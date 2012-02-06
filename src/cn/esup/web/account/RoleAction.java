package cn.esup.web.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.esup.common.AjaxResponse;
import cn.esup.entity.account.Authority;
import cn.esup.entity.account.Role;
import cn.esup.service.account.AccountManager;
import cn.esup.web.CrudActionSupport;

/**
 * 角色管理Action.
 * 
 * 演示不分页的简单管理界面.
 * 
 * @author calvin
 */
@Namespace("/account")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "role.action", type = "redirect") })
public class RoleAction extends CrudActionSupport<Role> {

	private static final long serialVersionUID = -4052047494894591406L;

	private AccountManager accountManager;

	// -- 页面属性 --//
	private Long id;
	private Role entity;
	private String ids;
	private String query;// query condition map string
	private int page;// jeasyui datagrid
	private int rows;// jeasyui datagrid

	//	private List<Role> allRoleList;// 角色列表
	//	private List<Long> checkedAuthIds;// 页面中钩选的权限id列表

	// -- ModelDriven 与 Preparable函数 --//
	public Role getModel() {
		return entity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accountManager.getRole(id);
		} else {
			entity = new Role();
		}
	}

	// -- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		//	allRoleList = accountManager.getAllRole();

		// 查询所有的角色树视图
		AjaxResponse.ajaxResp(accountManager.queryRoleTreeView());
		return null;
	}

	/** 通过角色id查询角色下面所有的权限列表视图 */
	public String authList() throws Exception {
		AjaxResponse.ajaxResp(accountManager.queryAuthGridView(id, query, page, rows));
		return null;
	}

	/** 用户角色列表视图 */
	public String roleList() throws Exception {
		AjaxResponse.ajaxResp(accountManager.queryRoleGridView(query, page, rows));
		return null;
	}

	@Override
	public String input() throws Exception {
		//	checkedAuthIds = entity.getAuthIds();
		AjaxResponse.ajaxResp(accountManager.getRole(id));
		return null;
	}

	@Override
	public String save() throws Exception {
		// 根据页面上的checkbox 整合Role的Authorities Set.
		//	HibernateUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthIds, Authority.class);

		// 保存用户并放入成功信息.
		accountManager.saveRole(entity);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 关联角色保存权限 */
	public String saveAuth() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String name = request.getParameter("name");
		Long roleId = Long.parseLong(request.getParameter("roleId"));
		String roleName = request.getParameter("roleName");
		String status = request.getParameter("status");

		Authority auth;
		if (request.getParameter("id").length() > 0) {// 修改权限
			Long id = Long.parseLong(request.getParameter("id"));
			auth = new Authority(id, name, roleId, roleName, status);
		} else {
			auth = new Authority(name, roleId, roleName, status);
		}

		accountManager.saveAuth(auth);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 给用户配置角色 */
	public String saveUserRole() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String userId = request.getParameter("userId");
		Long roleId = Long.parseLong(request.getParameter("roleId"));

		accountManager.saveUserRole(userId, roleId);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 删除用户角色 */
	public String deleteUserRole() throws Exception {
		accountManager.deleteUserRole(ids);// ids=userId,roleId
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String delete() throws Exception {
		accountManager.deleteRole(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除权限 */
	public String batchDeleteAuth() throws Exception {
		accountManager.batchDeleteAuth(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 切换权限状态 */
	public String toggleAuthStatus() throws Exception {
		accountManager.toggleAuthStatus(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 查询角色combobox */
	public String queryRoleCombobox() throws Exception {
		AjaxResponse.ajaxResp(accountManager.getAllRole());
		return null;
	}

	// -- 页面属性访问函数 --//
	/**
	 * list页面显示所有角色列表.
	 */
	//	public List<Role> getAllRoleList() {
	//		return allRoleList;
	//	}

	/**
	 * input页面显示所有授权列表.
	 */
	public List<Authority> getAllAuthorityList() {
		return accountManager.getAllAuthority();
	}

	/**
	 * input页面显示角色拥有的授权.
	 */
	//	public List<Long> getCheckedAuthIds() {
	//		return checkedAuthIds;
	//	}

	/**
	 * input页面提交角色拥有的授权.
	 */
	//	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
	//		this.checkedAuthIds = checkedAuthIds;
	//	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}