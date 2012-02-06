/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.payrecords;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * 应付款付款记录表 
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "payrecords")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Payrecords extends IdEntity {

	//所有属性

	/** 供货单号(供货单流水号) */
	private String supplySerialNumber;
	/** 应付表主键 */
	private Long payableId;
	/** 付款金额 */
	private Double payMoney;
	/** 付款日期 */
	private Date payDate;
	/** 财务用户账户 */
	private String userName;
	/** 备注 */
	private String remark;

	public String getSupplySerialNumber() {
		return this.supplySerialNumber;
	}

	public void setSupplySerialNumber(String value) {
		this.supplySerialNumber = value;
	}

	public Long getPayableId() {
		return this.payableId;
	}

	public void setPayableId(Long value) {
		this.payableId = value;
	}

	public Double getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(Double value) {
		this.payMoney = value;
	}

	public java.util.Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(java.util.Date value) {
		this.payDate = value;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}

}
