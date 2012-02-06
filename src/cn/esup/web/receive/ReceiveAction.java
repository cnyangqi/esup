/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.web.receive;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import cn.esup.common.AjaxResponse;
import cn.esup.common.BigDecimalTools;
import cn.esup.common.SS3Tools;
import cn.esup.entity.payable.Payable;
import cn.esup.entity.receive.Receive;
import cn.esup.entity.savingaccount.Savingaccount;
import cn.esup.entity.supply.Supply;
import cn.esup.service.payable.PayableManager;
import cn.esup.service.receive.ReceiveManager;
import cn.esup.service.savingaccount.SavingaccountManager;
import cn.esup.service.supply.SupplyManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/receive")
@Results({ @Result(name = "reload", location = "receive.action", type = "redirect") })
public class ReceiveAction extends CrudActionSupport<Receive> {

	private static final long serialVersionUID = 1L;
	private ReceiveManager receiveManager;
	private SupplyManager supplyManager;
	private SavingaccountManager savingaccountManager;
	private PayableManager payableManager;

	// - 页面属性 -//
	private Long id;
	private Receive receive;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Receive getModel() {
		return receive;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			receive = receiveManager.queryReceiveById(id);
		} else {
			receive = new Receive();
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
		AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(query, page, rows));// 单表
		//	AjaxResponse.ajaxResp(receiveManager.queryReceiveGridView(id, query, page, rows));// 主子表

		return null;
	}

	@Override
	public String delete() throws Exception {
		receiveManager.deleteReceive(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 批量删除对象 */
	public String batchDelete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		receiveManager.batchDeleteReceive(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		receive.setSerialNumber(SS3Tools.genSerialNumber());// 流水号
		receive.setDemand(receive.getTotal());// 剩余采购量
		receive.setPublishName(SpringSecurityUtils.getCurrentUser().getUsername());// 记录发布用户账户
		receiveManager.saveReceive(receive);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(receiveManager.queryReceiveById(id));
		return null;
	}

	/** 切换对象状态 */
	public String toggleReceiveStatus() throws Exception {
		receiveManager.toggleReceiveStatus(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 收货完成，自动生成财务应付款表 */
	public String genPayable() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reality = request.getParameter("reality");

		// 将实际收货量保存到供货表实际供应数量属性中
		Supply supply = supplyManager.querySupplyById(id);
		supply.setReality(Long.parseLong(reality));// 实际供货数量
		supply.setStatus("2");// 供货表状态更改为收货完成
		supply.setUpdateDate(new Date());
		supplyManager.saveSupply(supply);

		// 生成财务应付款表
		Payable payable = new Payable();
		payable.setSupplyId(supply.getId());// 供货单主键
		payable.setSupplySerialNumber(supply.getSerialNumber());// 供货单号（流水号）
		payable.setSupplyName(supply.getSupplyName());// 供货商用户账户
		payable.setTraderQq(supply.getTraderQq());// 供货商联系QQ
		payable.setTraderMobi(supply.getTraderMobi());// 供货商联系手机
		payable.setCategory(supply.getCategory());// 供货类型
		payable.setReality(supply.getReality());// 实际供货数量
		payable.setUnit(supply.getUnit());// 商品单位
		payable.setUnitPrice(supply.getUnitPrice());// 商品单价

		//	BigDecimal a = new BigDecimal(supply.getReality());
		//	BigDecimal b = new BigDecimal(supply.getUnitPrice());
		//	double result = a.multiply(b).doubleValue();

		double result = BigDecimalTools.mul(supply.getReality(), supply.getUnitPrice());
		payable.setTotalMoney(result);// 应付总金额
		payable.setUnpaid(result);// 未付总金额
		payable.setPaid(0D);// 已付总金额

		// 查找用户关联的默认账户
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_userName", supply.getSupplyName()));// 绑定供货商用户账户
		filters.add(new PropertyFilter("EQS_status", "1"));// 默认收货地址
		List<Savingaccount> list = savingaccountManager.querySavingaccountByFilters(filters);
		Savingaccount sc = list.get(0);
		payable.setAccountBank(sc.getAccountBank());// 开户行
		payable.setAccountName(sc.getAccountName());// 收款人
		payable.setAccount(sc.getAccount());// 收款账户

		payable.setGenDate(new Date());// 应付记录生成时间
		payableManager.savePayable(payable);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Autowired
	public void setReceiveManager(ReceiveManager receiveManager) {
		this.receiveManager = receiveManager;
	}

	@Autowired
	public void setSupplyManager(SupplyManager supplyManager) {
		this.supplyManager = supplyManager;
	}

	@Autowired
	public void setSavingaccountManager(SavingaccountManager savingaccountManager) {
		this.savingaccountManager = savingaccountManager;
	}

	@Autowired
	public void setPayableManager(PayableManager payableManager) {
		this.payableManager = payableManager;
	}

	public Long getId() {
		return id;
	}

}
