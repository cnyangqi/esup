/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */

package cn.esup.web.datadictionary;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.esup.common.AjaxResponse;
import cn.esup.common.Cn2Spell;
import cn.esup.entity.datadictionary.DataDictionaryType;
import cn.esup.service.datadictionary.DataDictionaryTypeManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/datadictionary")
@Results({ @Result(name = "reload", location = "DataDictionaryType.action", type = "redirect") })
public class DatadictionarytypeAction extends CrudActionSupport<DataDictionaryType> {

	private static final long serialVersionUID = 1L;
	private DataDictionaryTypeManager dataDictionaryTypeManager;

	// - 页面属性 -//
	private Long id;
	private String ids;
	private DataDictionaryType dataDictionaryType;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public DataDictionaryType getModel() {
		return dataDictionaryType;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			dataDictionaryType = dataDictionaryTypeManager.queryDataDictionaryTypeById(id);
		} else {
			dataDictionaryType = new DataDictionaryType();
		}
	}

	// - CRUD Action 函数 -//
	@Override
	public String list() throws Exception {

		// 将父节点的主键作为子节点的查询条件（Long parentId）
		AjaxResponse.ajaxResp(dataDictionaryTypeManager.queryDataDictionaryTypeTreeView(id));
		return null;
	}

	@Override
	public String delete() throws Exception {
		dataDictionaryTypeManager.deleteDataDictionaryType(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	public String batchDelete() throws Exception {
		dataDictionaryTypeManager.batchDeleteDataDictionaryType(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		dataDictionaryType.setCode(Cn2Spell.converterToFirstSpell(dataDictionaryType.getName()));// 自动生成类型代码
		dataDictionaryTypeManager.saveDataDictionaryType(dataDictionaryType);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(dataDictionaryTypeManager.queryDataDictionaryTypeById(id));
		return null;
	}

	@Autowired
	public void setDataDictionaryTypeManager(DataDictionaryTypeManager dataDictionaryTypeManager) {
		this.dataDictionaryTypeManager = dataDictionaryTypeManager;
	}

	public Long getId() {
		return id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}