/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.savingaccount;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * 表主键 
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "savingaccount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Savingaccount extends IdEntity {

	//所有属性

	/** 用户账户 */
	private String userName;
	/** 账户类型 银行/支付宝 */
	private String accountType;
	/** 开户行 */
	private String accountBank;
	/** 收款人 */
	private String accountName;
	/** 收款账户 */
	private String account;
	/** 创建/更新日期 */
	private Date updateDate;
	/** 账户状态 默认收款账户*/
	private String status;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String value) {
		this.accountType = value;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String value) {
		this.accountBank = value;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String value) {
		this.accountName = value;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String value) {
		this.account = value;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
