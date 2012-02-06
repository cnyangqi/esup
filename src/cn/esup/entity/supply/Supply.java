/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.supply;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 *  供货表
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Supply extends IdEntity {

	//所有属性

	/** 流水号 */
	private String serialNumber;
	/** 收货表ID */
	private Long receiveId;
	/** 收货类型 */
	private String category;
	/** 游戏名称 */
	private String game;
	/** 游戏区名 */
	private String area;
	/** 游戏服务器名称 */
	private String server;
	/** 单价 */
	private Double unitPrice;
	/** 商品单位 */
	private String unit;
	/** 收货方联系QQ */
	private String receiveQq;
	/** 供应商账户 */
	private String supplyName;
	/** 出货角色名称 */
	private String dealName;
	/** 供应数量 */
	private Long quantity;
	/** 实际供应数量 */
	private Long reality;
	/** 出货方联系QQ */
	private String traderQq;
	/** 出货方联系手机 */
	private String traderMobi;
	/** 提交时间 */
	private Date submitDate;
	/** 收货完成时间 */
	private Date updateDate;
	/** 备注 */
	private String remark;
	/** 交易状态 正在收货/收货完成/撤销供货/已结货款 */
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

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReceiveQq() {
		return receiveQq;
	}

	public void setReceiveQq(String receiveQq) {
		this.receiveQq = receiveQq;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getReality() {
		return reality;
	}

	public void setReality(Long reality) {
		this.reality = reality;
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

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
