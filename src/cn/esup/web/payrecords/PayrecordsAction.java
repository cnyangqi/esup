/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.web.payrecords;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.esup.common.AjaxResponse;
import cn.esup.entity.payable.Payable;
import cn.esup.entity.payrecords.Payrecords;
import cn.esup.entity.supply.Supply;
import cn.esup.service.payable.PayableManager;
import cn.esup.service.payrecords.PayrecordsManager;
import cn.esup.service.supply.SupplyManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/payrecords")
@Results({ @Result(name = "reload", location = "payrecords.action", type = "redirect") })
public class PayrecordsAction extends CrudActionSupport<Payrecords> {

	private static final long serialVersionUID = 1L;
	private PayrecordsManager payrecordsManager;
	private PayableManager payableManager;
	private SupplyManager supplyManager;

	// - 页面属性 -//
	private Long id;
	private Payrecords payrecords;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Payrecords getModel() {
		return payrecords;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			payrecords = payrecordsManager.queryPayrecordsById(id);
		} else {
			payrecords = new Payrecords();
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
		AjaxResponse.ajaxResp(payrecordsManager.queryPayrecordsGridView(query, page, rows));// 单表
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(id, query, page, rows));// 主子表

		return null;
	}

	@Override
	public String delete() throws Exception {
		payrecordsManager.deletePayrecords(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除对象 */
	public String batchDelete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		payrecordsManager.batchDeletePayrecords(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		payrecords.setUserName(SpringSecurityUtils.getCurrentUserName());
		payrecords.setPayDate(new Date());
		payrecordsManager.savePayrecords(payrecords);

		// 应付款
		Payable payable = payableManager.queryPayableById(payrecords.getPayableId());
		payable.setPaid(payable.getPaid() + payrecords.getPayMoney());// 更新已付总金额
		payable.setUnpaid(payable.getUnpaid() - payrecords.getPayMoney());// 更新未付总金额
		payableManager.savePayable(payable);

		// 供货单
		Supply supply = supplyManager.querySupplyById(payable.getSupplyId());
		if (payable.getUnpaid() == 0) {
			supply.setStatus("5");// 结清货款
		} else {
			supply.setStatus("4");// 分期付款中
		}
		supplyManager.saveSupply(supply);

		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(payrecordsManager.queryPayrecordsById(id));
		return null;
	}

	/** 切换对象状态 */
	public String togglePayrecordsStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		payrecordsManager.togglePayrecordsStatus(id, status);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Autowired
	public void setPayrecordsManager(PayrecordsManager payrecordsManager) {
		this.payrecordsManager = payrecordsManager;
	}

	@Autowired
	public void setPayableManager(PayableManager payableManager) {
		this.payableManager = payableManager;
	}

	@Autowired
	public void setSupplyManager(SupplyManager supplyManager) {
		this.supplyManager = supplyManager;
	}

	public Long getId() {
		return id;
	}

}
