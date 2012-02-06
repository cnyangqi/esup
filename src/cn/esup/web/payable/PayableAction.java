/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.web.payable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.esup.common.AjaxResponse;
import cn.esup.entity.payable.Payable;
import cn.esup.service.payable.PayableManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/payable")
@Results({ @Result(name = "reload", location = "payable.action", type = "redirect") })
public class PayableAction extends CrudActionSupport<Payable> {

	private static final long serialVersionUID = 1L;
	private PayableManager payableManager;

	// - 页面属性 -//
	private Long id;
	private Payable payable;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Payable getModel() {
		return payable;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			payable = payableManager.queryPayableById(id);
		} else {
			payable = new Payable();
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
		int page = Integer.parseInt(request.getParameter("page"));// jeasyui datagrid
		int rows = Integer.parseInt(request.getParameter("rows"));// jeasyui datagrid
		AjaxResponse.ajaxResp(payableManager.queryPayableGridView(query, page, rows));// 单表
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(id, query, page, rows));// 主子表

		return null;
	}

	@Override
	public String delete() throws Exception {
		payableManager.deletePayable(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除对象 */
	public String batchDelete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		payableManager.batchDeletePayable(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		payableManager.savePayable(payable);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(payableManager.queryPayableById(id));
		return null;
	}

	/** 切换对象状态 */
	public String togglePayableStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		payableManager.togglePayableStatus(id, status);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Autowired
	public void setPayableManager(PayableManager payableManager) {
		this.payableManager = payableManager;
	}

	public Long getId() {
		return id;
	}

}
