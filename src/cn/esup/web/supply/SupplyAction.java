/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.web.supply;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.esup.common.AjaxResponse;
import cn.esup.common.SS3Tools;
import cn.esup.entity.receive.Receive;
import cn.esup.entity.supply.Supply;
import cn.esup.service.receive.ReceiveManager;
import cn.esup.service.supply.SupplyManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/supply")
@Results({ @Result(name = "reload", location = "supply.action", type = "redirect") })
public class SupplyAction extends CrudActionSupport<Supply> {

	private static final long serialVersionUID = 1L;
	private SupplyManager supplyManager;
	private ReceiveManager receiveManager;

	// - 页面属性 -//
	private Long id;
	private Supply supply;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Supply getModel() {
		return supply;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			supply = supplyManager.querySupplyById(id);
		} else {
			supply = new Supply();
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
		AjaxResponse.ajaxResp(supplyManager.querySupplyGridView(query, page, rows));// 单表
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(id, query, page, rows));// 主子表

		return null;
	}

	@Override
	public String delete() throws Exception {
		supplyManager.deleteSupply(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除对象 */
	public String batchDelete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		supplyManager.batchDeleteSupply(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 供货单一旦生成只能修改其状态属性 */
	@Override
	public String save() throws Exception {

		supply.setSerialNumber(SS3Tools.genSerialNumber());// 生成供货单流水号
		supply.setSupplyName(SpringSecurityUtils.getCurrentUserName());
		supply.setSubmitDate(new Date());
		supply.setStatus("1");// 默认供货单交易状态为正在收货
		supplyManager.saveSupply(supply);

		Receive receive = receiveManager.queryReceiveById(supply.getReceiveId());
		receive.setDemand(receive.getDemand() - supply.getQuantity());// 更新收货表剩余收货数量
		receiveManager.saveReceive(receive);

		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(supplyManager.querySupplyById(id));
		return null;
	}

	/** 切换对象状态 */
	public String toggleSupplyStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		String receiveId = request.getParameter("receiveId");
		String quantity = request.getParameter("quantity");

		if (receiveId.length() > 0) {// 供货商撤单
			Receive receive = receiveManager.queryReceiveById(Long.parseLong(receiveId));
			receive.setDemand(receive.getDemand() + Long.parseLong(quantity));
			receiveManager.saveReceive(receive);
		}

		supplyManager.toggleSupplyStatus(id, status);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Autowired
	public void setSupplyManager(SupplyManager supplyManager) {
		this.supplyManager = supplyManager;
	}

	@Autowired
	public void setReceiveManager(ReceiveManager receiveManager) {
		this.receiveManager = receiveManager;
	}

	public Long getId() {
		return id;
	}

}
