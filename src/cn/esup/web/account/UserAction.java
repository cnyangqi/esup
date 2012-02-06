package cn.esup.web.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.esup.common.AjaxResponse;
import cn.esup.common.SS3Tools;
import cn.esup.entity.account.Role;
import cn.esup.entity.account.User;
import cn.esup.service.ServiceException;
import cn.esup.service.account.AccountManager;
import cn.esup.web.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 * @author calvin
 */
// 定义URL映射对应/account/user.action
@Namespace("/account")
// 定义名为reload的result重定向到user.action, 其他result则按照convention默认.
@Results({@Result(name = CrudActionSupport.RELOAD, location = "user.action", type = "redirect")})
public class UserAction extends CrudActionSupport<User> {

	private static final long serialVersionUID = 8683878162525847072L;

	private AccountManager accountManager;

	// -- 页面属性 --//
	private Long id;
	private User entity;
	private String ids;
	private String query;// query condition map string
	private int page;// jeasyui datagrid
	private int rows;// jeasyui datagrid

	// private Page<User> page = new Page<User>(5);//每页5条记录
	private List<Long> checkedRoleIds; // 页面中钩选的角色id列表

	// -- ModelDriven 与 Preparable函数 --//
	public void setId(Long id) {
		this.id = id;
	}

	public User getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accountManager.getUser(id);
		} else {
			entity = new User();
		}
	}

	// -- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		// List<PropertyFilter> filters =
		// PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());

		// 设置默认排序方式
		// if (!page.isOrderBySetted()) {
		// page.setOrderBy("id");
		// page.setOrder(Page.ASC);
		// }

		// page = accountManager.searchUser(page, filters);

		// 将主表对象主键作为子表对象的分页查询条件（Long typeId）
		AjaxResponse.ajaxResp(accountManager.queryUserGridView(id, query, page, rows));
		return null;
	}

	@Override
	public String input() throws Exception {
		checkedRoleIds = entity.getRoleIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		// 根据页面上的checkbox选择 整合User的Roles Set
		// HibernateUtils.mergeByCheckedIds(entity.getRoleList(),
		// checkedRoleIds, Role.class);

		if (entity.getStatus() == null) {// 前台注册人员须要等待后台管理用户激活账户
			entity.setStatus("0");
		}

		if (entity.getId() == null) {
			entity.setPassword(SS3Tools.getMD5Str(entity.getPassword()));
		} else {// 用户修改
			if (entity.getPassword().length() != 32) {// 密码被修改
				entity.setPassword(SS3Tools.getMD5Str(entity.getPassword()));
			}
		}

		accountManager.saveUser(entity);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String delete() throws Exception {
		try {
			accountManager.deleteUser(id);
		}
		catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		AjaxResponse.ajaxResp(true);
		return null;
	}

	public String batchDelete() throws Exception {
		accountManager.batchDeleteUser(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	// -- 其他Action函数 --//
	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkLoginName() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String newLoginName = request.getParameter("loginName");
		// String oldLoginName = request.getParameter("oldLoginName");

		// if (accountManager.isLoginNameUnique(newLoginName, oldLoginName)) {
		// Struts2Utils.renderText("true");
		// } else {
		// Struts2Utils.renderText("false");
		// }

		AjaxResponse.ajaxResp(accountManager.isLoginNameUnique(newLoginName, null));

		// 因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}

	/** 通过用户主键查询用户 */
	public String queryUserById() throws Exception {
		AjaxResponse.ajaxResp(accountManager.getUser(id));
		return null;
	}

	/** 切换用户状态 */
	public String toggleUserStatus() throws Exception {
		accountManager.toggleUserStatus(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	// -- 页面属性访问函数 --//
	/**
	 * list页面显示用户分页列表.
	 */
	// public Page<User> getPage() {
	// return page;
	// }

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

	public Long getId() {
		return id;
	}

	/**
	 * input页面显示所有角色列表.
	 */
	public List<Role> getAllRoleList() {
		return accountManager.getAllRole();
	}

	/**
	 * input页面显示用户拥有的角色.
	 */
	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	/**
	 * input页面提交用户拥有的角色.
	 */
	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}

// 1. 保存对象返回JSON数据，ie6会报“文档的顶层无效”错误。
