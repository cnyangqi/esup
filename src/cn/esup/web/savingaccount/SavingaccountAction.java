/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.web.savingaccount;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.esup.common.AjaxResponse;
import cn.esup.entity.savingaccount.Savingaccount;
import cn.esup.service.savingaccount.SavingaccountManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/savingaccount")
@Results({ @Result(name = "reload", location = "savingaccount.action", type = "redirect") })
public class SavingaccountAction extends CrudActionSupport<Savingaccount> {

	private static final long serialVersionUID = 1L;
	private SavingaccountManager savingaccountManager;

	// - 页面属性 -//
	private Long id;
	private Savingaccount savingaccount;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Savingaccount getModel() {
		return savingaccount;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			savingaccount = savingaccountManager.querySavingaccountById(id);
		} else {
			savingaccount = new Savingaccount();
		}
	}

	// - CRUD Action 函数 -//
	@Override
	public String list() throws Exception {

		// 将父亲节点的主键作为子节点的查询条件（Long parentId）
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveTreeView(id));

		//	单对象的分页查询
		HttpServletRequest request = ServletActionContext.getRequest();
		String query = request.getParameter("query");// query condition map string

		// 供货用户等只能查看与自己关联的财务账户，超级管理员账户除外
		if (!SpringSecurityUtils.getCurrentUserName().equalsIgnoreCase("admin")) {
			StringBuilder sb = new StringBuilder(query);
			sb.deleteCharAt(sb.length() - 1);
			sb.append(",");
			sb.append("\"EQS_userName\":\"").append(SpringSecurityUtils.getCurrentUserName()).append("\"");
			sb.append("}");
			query = sb.toString();
		}

		int page = Integer.parseInt(request.getParameter("page"));// jeasyui datagrid
		int rows = Integer.parseInt(request.getParameter("rows"));// jeasyui datagrid
		AjaxResponse.ajaxResp(savingaccountManager.querySavingaccountGridView(query, page, rows));// 单表
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(id, query, page, rows));// 主子表

		return null;
	}

	@Override
	public String delete() throws Exception {
		savingaccountManager.deleteSavingaccount(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除对象 */
	public String batchDelete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		savingaccountManager.batchDeleteSavingaccount(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		savingaccount.setUserName(SpringSecurityUtils.getCurrentUserName());
		savingaccount.setUpdateDate(new Date());
		savingaccountManager.saveSavingaccount(savingaccount);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(savingaccountManager.querySavingaccountById(id));
		return null;
	}

	/** 切换对象状态 */
	public String toggleSavingaccountStatus() throws Exception {
		savingaccountManager.toggleSavingaccountStatus(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Autowired
	public void setSavingaccountManager(SavingaccountManager savingaccountManager) {
		this.savingaccountManager = savingaccountManager;
	}

	public Long getId() {
		return id;
	}

}
