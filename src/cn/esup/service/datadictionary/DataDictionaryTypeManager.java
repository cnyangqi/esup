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
import cn.esup.dao.datadictionary.DataDictionaryTypeDao;
import cn.esup.entity.datadictionary.DataDictionaryType;
import cn.esup.entity.jeasyui.TreeNode;

/**
 * 数据字典类型表 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class DataDictionaryTypeManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(DataDictionaryTypeManager.class);

	/** dao对象 */
	private DataDictionaryTypeDao dataDictionaryTypeDao;

	/** 保存新增或修改的对象 */
	public void saveDataDictionaryType(DataDictionaryType dataDictionaryType) {

		// 如果新增的数据字典类型存在父级类型主键，则该父级数据字典类型的子类型数加1
		if (dataDictionaryType.getId() == null && dataDictionaryType.getParentId() != null) {
			DataDictionaryType tmp = dataDictionaryTypeDao.get(dataDictionaryType.getParentId());
			if (tmp.getSubTypeNum() != null) {
				tmp.setSubTypeNum(tmp.getSubTypeNum() + 1);
			} else {
				tmp.setSubTypeNum(1L);
			}
			dataDictionaryTypeDao.save(tmp);
		}

		dataDictionaryTypeDao.save(dataDictionaryType);
	}

	/** 按id删除对象 */
	public void deleteDataDictionaryType(Long id) {

		// 如果删除的数据字典类型存在父级数据字典类型，则该父级数据字典类型的子类型数减1
		DataDictionaryType dataDictionaryType = dataDictionaryTypeDao.get(id);
		if (dataDictionaryType.getParentId() != null) {
			DataDictionaryType tmp = dataDictionaryTypeDao.get(dataDictionaryType.getParentId());
			tmp.setSubTypeNum(tmp.getSubTypeNum() - 1);
			dataDictionaryTypeDao.save(tmp);
		}

		// 删除该数据字典类型下属所有数据字典类型
		dataDictionaryTypeDao.batchExecute("delete DataDictionaryType t where t.parentId = ?", id);

		// 删除该数据字典类型本身
		dataDictionaryTypeDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteDataDictionaryType(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		dataDictionaryTypeDao.batchExecute("delete DataDictionaryType t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public DataDictionaryType queryDataDictionaryTypeById(Long id) {
		return dataDictionaryTypeDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<DataDictionaryType> queryAllDataDictionaryType() {
		return dataDictionaryTypeDao.getAll();
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<DataDictionaryType> queryDataDictionaryTypeByFilters(final Page<DataDictionaryType> page,
			final List<PropertyFilter> filters) {
		return dataDictionaryTypeDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isDataDictionaryTypeUnique(String newDataDictionaryType, String oldDataDictionaryType) {
		return dataDictionaryTypeDao.isPropertyUnique("name", newDataDictionaryType, oldDataDictionaryType);
	}

	/** 查询数据字典类型树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryDataDictionaryTypeTreeView(Long parentId) {
		Iterator<DataDictionaryType> it = dataDictionaryTypeDao.queryDataDictionaryTypeTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			DataDictionaryType tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			node.setText(tmp.getName());

			// 当数据字典类型对象的子类型数大于0的时候，表示该类型节点还有子节点，状态为closed
			Long subTypeNum = tmp.getSubTypeNum();
			if (subTypeNum != null && subTypeNum > 0) {
				node.setState(TreeNode.CLOSED);
			} else {
				node.setState(TreeNode.OPEN);
			}

			// 设置树节点属性
			Map<String, String> map = new HashMap<String, String>();
			map.put("parentId", String.valueOf(tmp.getParentId()));
			map.put("parentName", String.valueOf(tmp.getParentName()));
			map.put("sequNum", String.valueOf(tmp.getSequNum()));
			map.put("subTypeNum", String.valueOf(tmp.getSubTypeNum()));
			map.put("subDdNum", String.valueOf(tmp.getSubDdNum()));
			node.setAttributes(map);

			list.add(node);
		}
		return list;
	}

	/** 注入dao对象 */
	@Autowired
	public void setDataDictionaryTypeDao(DataDictionaryTypeDao dataDictionaryTypeDao) {
		this.dataDictionaryTypeDao = dataDictionaryTypeDao;
	}
}
