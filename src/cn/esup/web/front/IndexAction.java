package cn.esup.web.front;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;

import cn.esup.entity.datadictionary.DataDictionary;
import cn.esup.entity.receive.Receive;
import cn.esup.service.datadictionary.DataDictionaryManager;
import cn.esup.service.receive.ReceiveManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台首页action
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */

@Namespace("/front")
public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private ReceiveManager receiveManager;
	private DataDictionaryManager dataDictionaryManager;
	private List<Receive> top10List;

	@Override
	public String execute() throws Exception {
		this.setTop10List(receiveManager.quertTop10Record());

		Map<String, List<DataDictionary>> map = queryAllDataDictionaryByTypeCode("splx");
		List<DataDictionary> list = map.get("splx");

		// 对比更换为数据字典名称
		for (Receive temp1 : top10List) {
			for (DataDictionary temp2 : list) {
				if (temp2.getValue().equalsIgnoreCase(temp1.getCategory())) {
					temp1.setCategory(temp2.getName());
				}
			}
		}

		return SUCCESS;
	}

	public List<Receive> getTop10List() {
		return top10List;
	}

	public void setTop10List(List<Receive> top10List) {
		this.top10List = top10List;
	}

	/** 通过数据字典类型代码查询该类型下所有的数据字典 */
	public Map<String, List<DataDictionary>> queryAllDataDictionaryByTypeCode(String typeCodes) throws Exception {

		if (typeCodes != null && !typeCodes.equalsIgnoreCase("")) {
			String[] temp = typeCodes.split(",");
			Map<String, List<DataDictionary>> map = new HashMap<String, List<DataDictionary>>();
			for (String code : temp) {
				List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_typeCode", code));
				map.put(code, dataDictionaryManager.queryDataDictionaryByFilters(filters));
			}
			return map;
		}

		return null;
	}

	@Autowired
	public void setReceiveManager(ReceiveManager receiveManager) {
		this.receiveManager = receiveManager;
	}

	@Autowired
	public void setDataDictionaryManager(DataDictionaryManager dataDictionaryManager) {
		this.dataDictionaryManager = dataDictionaryManager;
	}

}
