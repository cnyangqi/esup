/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */
package cn.esup.service.datadictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;

import cn.esup.common.SS3Tools;
import cn.esup.dao.datadictionary.DataDictionaryDao;
import cn.esup.entity.datadictionary.DataDictionary;
import cn.esup.entity.jeasyui.TreeNode;

/**
 * 数据字典表 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class DataDictionaryManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(DataDictionaryTypeManager.class);

	/** dao对象 */
	private DataDictionaryDao dataDictionaryDao;

	/** 保存新增或修改的对象 */
	public void saveDataDictionary(DataDictionary dataDictionary) {
		if (dataDictionary.getId() == null) {
			dataDictionary.setStatus("1");// 新增数据字典的时候默认启用数据字典
		}
		dataDictionaryDao.save(dataDictionary);
	}

	/** 按id删除对象 */
	public void deleteDataDictionary(Long id) {
		dataDictionaryDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteDataDictionary(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		dataDictionaryDao.batchExecute("delete DataDictionary t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public DataDictionary queryDataDictionaryById(Long id) {
		return dataDictionaryDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<DataDictionary> queryAllDataDictionary() {
		return dataDictionaryDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<DataDictionary> queryDataDictionaryByFilters(final List<PropertyFilter> filters) {
		return dataDictionaryDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<DataDictionary> queryDataDictionaryByFilters(final Page<DataDictionary> page,
			final List<PropertyFilter> filters) {
		return dataDictionaryDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isDataDictionaryUnique(String newDataDictionary, String oldDataDictionary) {
		return dataDictionaryDao.isPropertyUnique("name", newDataDictionary, oldDataDictionary);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryDataDictionaryTreeView(Long parentId) {
		Iterator<DataDictionary> it = dataDictionaryDao.queryDataDictionaryTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			DataDictionary tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			node.setText(tmp.getName());

			// 当数据字典类型对象的子类型数大于0的时候，表示该类型节点还有子节点，状态为closed
			//Long subTypeNum = tmp.getSubTypeNum();
			//if (subTypeNum != null && subTypeNum > 0) {
			//	node.setState(TreeNode.CLOSED);
			//} else {
			//	node.setState(TreeNode.OPEN);
			//}

			// 设置树节点属性
			Map<String, String> map = new HashMap<String, String>();
			//map.put("parentId", String.valueOf(tmp.getParentId()));
			//map.put("sequNum", String.valueOf(tmp.getSequNum()));
			//map.put("subTypeNum", String.valueOf(tmp.getSubTypeNum()));
			//map.put("subDdNum", String.valueOf(tmp.getSubDdNum()));
			node.setAttributes(map);

			list.add(node);
		}
		return list;
	}

	/** 查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryDataDictionaryGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<DataDictionary> page = new Page<DataDictionary>(pageSize);
		page.setPageNo(pageNo);
		page = dataDictionaryDao.queryDataDictionaryGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换数据字典状态 */
	public void toggleDatadictionaryStatus(Long id) {
		DataDictionary dataDictionary = dataDictionaryDao.get(id);
		if (dataDictionary.getStatus().equalsIgnoreCase("1")) {
			dataDictionary.setStatus("0");
		} else {
			dataDictionary.setStatus("1");
		}
		dataDictionaryDao.save(dataDictionary);
	}

	/** 注入dao对象 */
	@Autowired
	public void setDataDictionaryDao(DataDictionaryDao dataDictionaryDao) {
		this.dataDictionaryDao = dataDictionaryDao;
	}

}
