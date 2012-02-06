/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */

package cn.esup.entity.datadictionary;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * 数据字典类型表
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "DATA_DICTIONARY_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DataDictionaryType extends IdEntity {

	/** 类型名称 */
	@JsonProperty("text")
	private String name;

	/** 类型代码 */
	private String code;

	/** 类型图标 */
	private String iconCls;

	/** 父级类型主键 */
	private Long parentId;

	/** 父级类型名称 */
	private String parentName;

	/** 排序号 */
	private Long sequNum;

	/** 子类型数 */
	private Long subTypeNum;

	/** 子数据字典数 */
	private Long subDdNum;

	/** 扩展字段1 */
	private String extendField1;

	/** 扩展字段2 */
	private String extendField2;

	/** 扩展字段3 */
	private String extendField3;

	/** 树节点状态 open/closed */
	private String state;

	/** 树节点属性 */
	private Map<String, String> attributes;

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long value) {
		this.parentId = value;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getSequNum() {
		return this.sequNum;
	}

	public void setSequNum(Long value) {
		this.sequNum = value;
	}

	public Long getSubTypeNum() {
		return subTypeNum;
	}

	public void setSubTypeNum(Long subTypeNum) {
		this.subTypeNum = subTypeNum;
	}

	public Long getSubDdNum() {
		return subDdNum;
	}

	public void setSubDdNum(Long subDdNum) {
		this.subDdNum = subDdNum;
	}

	public String getExtendField1() {
		return this.extendField1;
	}

	public void setExtendField1(String value) {
		this.extendField1 = value;
	}

	public String getExtendField2() {
		return this.extendField2;
	}

	public void setExtendField2(String value) {
		this.extendField2 = value;
	}

	public String getExtendField3() {
		return this.extendField3;
	}

	public void setExtendField3(String value) {
		this.extendField3 = value;
	}

	@Transient
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Transient
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
