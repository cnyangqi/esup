/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */

package cn.esup.web.datadictionary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;

import cn.esup.common.AjaxResponse;
import cn.esup.common.Cn2Spell;
import cn.esup.entity.datadictionary.DataDictionary;
import cn.esup.service.datadictionary.DataDictionaryManager;
import cn.esup.web.CrudActionSupport;

/**
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 * @version 1.0
 * @since 1.0
 */

@Namespace("/datadictionary")
@Results({ @Result(name = "reload", location = "datadictionary.action", type = "redirect") })
public class DatadictionaryAction extends CrudActionSupport<DataDictionary> {

	private static final long serialVersionUID = 1L;
	private DataDictionaryManager dataDictionaryManager;

	// - 页面属性 -//
	private Long id;
	private String ids;
	private String query;// query condition map string
	private int page;// jeasyui datagrid
	private int rows;// jeasyui datagrid
	private DataDictionary dataDictionary;

	// - ModelDriven 与 Preparable函数 -//
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public DataDictionary getModel() {
		return dataDictionary;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			dataDictionary = dataDictionaryManager.queryDataDictionaryById(id);
		} else {
			dataDictionary = new DataDictionary();
		}
	}

	// - CRUD Action 函数 -//
	@Override
	public String list() throws Exception {
		// 将主表对象主键作为子表对象的分页查询条件（Long typeId）
		AjaxResponse.ajaxResp(dataDictionaryManager.queryDataDictionaryGridView(id, query, page, rows));
		return null;
	}

	@Override
	public String delete() throws Exception {
		dataDictionaryManager.deleteDataDictionary(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	public String batchDelete() throws Exception {
		dataDictionaryManager.batchDeleteDataDictionary(ids);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String save() throws Exception {
		dataDictionary.setTypeCode(Cn2Spell.converterToFirstSpell(dataDictionary.getTypeName()));// 自动生成数据字典类型代码
		dataDictionaryManager.saveDataDictionary(dataDictionary);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	@Override
	public String input() throws Exception {
		AjaxResponse.ajaxResp(dataDictionaryManager.queryDataDictionaryById(id));
		return null;
	}

	/** 切换数据字典状态 */
	public String toggleDataDictionaryStatus() throws Exception {
		dataDictionaryManager.toggleDatadictionaryStatus(id);
		AjaxResponse.ajaxResp(true);
		return null;
	}

	/** 通过数据字典类型代码查询该类型下所有的数据字典 */
	public String queryAllDataDictionaryByTypeCode() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//String typeCode = new String(request.getParameter("typeCode").getBytes("ISO-8859-1"), "UTF-8");

		String typeCodes = request.getParameter("typeCodes");// 数据字典类型代码字符串列表，以,分割
		if (typeCodes != null && !typeCodes.equalsIgnoreCase("")) {
			String[] temp = typeCodes.split(",");
			Map<String, List<DataDictionary>> map = new HashMap<String, List<DataDictionary>>();
			for (String code : temp) {
				List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_typeCode", code));
				map.put(code, dataDictionaryManager.queryDataDictionaryByFilters(filters));
			}
			AjaxResponse.ajaxResp(map);
		}

		return null;
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

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Autowired
	public void setDataDictionaryManager(DataDictionaryManager dataDictionaryManager) {
		this.dataDictionaryManager = dataDictionaryManager;
	}

}
