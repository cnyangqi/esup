/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.payable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * 应付款表 
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "payable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Payable extends IdEntity {

	//所有属性

	/** 供货表主键 */
	private Long supplyId;
	/** 供货单号(供货单流水号) */
	private String supplySerialNumber;
	/** 供货商用户账户 */
	private String supplyName;
	/** 付款开户行 */
	private String accountBank;
	/** 付款收款人 */
	private String accountName;
	/** 付款收款账户 */
	private String account;
	/** 供货商QQ */
	private String traderQq;
	/** 供货商手机 */
	private String traderMobi;
	/** 供货类型 */
	private String category;
	/** 实际供货数量 */
	private Long reality;
	/** 商品单位 */
	private String unit;
	/** 商品单价 */
	private Double unitPrice;
	/** 应付总金额 */
	private Double totalMoney;
	/** 已支付金额 */
	private Double paid;
	/** 未支付金额 */
	private Double unpaid;
	/** 付款次数 */
	private Long payTime;
	/** 最后一次付款日期 */
	private Date updateDate;
	/** 应付记录生成日期 */
	private Date genDate;
	/** 备注 */
	private String remark;

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplySerialNumber() {
		return supplySerialNumber;
	}

	public void setSupplySerialNumber(String supplySerialNumber) {
		this.supplySerialNumber = supplySerialNumber;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTraderQq() {
		return traderQq;
	}

	public void setTraderQq(String traderQq) {
		this.traderQq = traderQq;
	}

	public String getTraderMobi() {
		return traderMobi;
	}

	public void setTraderMobi(String traderMobi) {
		this.traderMobi = traderMobi;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getReality() {
		return reality;
	}

	public void setReality(Long reality) {
		this.reality = reality;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Double getUnpaid() {
		return unpaid;
	}

	public void setUnpaid(Double unpaid) {
		this.unpaid = unpaid;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getGenDate() {
		return genDate;
	}

	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
