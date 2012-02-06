/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.receive;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * 收货表 
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "receive")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Receive extends IdEntity {

	//所有属性

	/** 流水号 */
	private String serialNumber;
	/** 业务类型  网游类/充值类/实物类 */
	private String businessType;
	/** 收货类型 装备/游戏币/游戏账户/点卡/游戏密保卡/游戏代练/点券/其他 */
	private String category;
	/** 游戏名 */
	private String game;
	/** 游戏区名 */
	private String area;
	/** 游戏服务器名 */
	private String server;
	/** 采购总量 */
	private Long total;
	/** 计量规格 */
	private String unit;
	/** 单价 */
	private Double unitPrice;
	/** 剩余采购量 */
	private Long demand;
	/** 单笔交易下限 */
	private Long minSupply;
	/** 收货方式 见面/邮寄 */
	private String traderMode;
	/** 收货联系QQ */
	private String traderQq;
	/** 发布日期 */
	private Date publicDate;
	/** 截止日期 */
	private Date expirationDate;
	/** 发布用户名称 */
	private String publishName;
	/** 备注 */
	private String remark;
	/** 审核状态 */
	private String status;
	/** 扩展字段1 */
	private String extendField1;
	/** 扩展字段2 */
	private String extendField2;
	/** 扩展字段3 */
	private String extendField3;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
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

	public Long getDemand() {
		return demand;
	}

	public void setDemand(Long demand) {
		this.demand = demand;
	}

	public Long getMinSupply() {
		return minSupply;
	}

	public void setMinSupply(Long minSupply) {
		this.minSupply = minSupply;
	}

	public String getTraderMode() {
		return traderMode;
	}

	public void setTraderMode(String traderMode) {
		this.traderMode = traderMode;
	}

	public String getTraderQq() {
		return traderQq;
	}

	public void setTraderQq(String traderQq) {
		this.traderQq = traderQq;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtendField1() {
		return extendField1;
	}

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	public String getExtendField2() {
		return extendField2;
	}

	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}

	public String getExtendField3() {
		return extendField3;
	}

	public void setExtendField3(String extendField3) {
		this.extendField3 = extendField3;
	}

}
