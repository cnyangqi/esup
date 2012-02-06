/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.supply;

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
import cn.esup.dao.supply.SupplyDao;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.entity.supply.Supply;

/**
 *  管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class SupplyManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Supply.class);

	/** dao对象 */
	private SupplyDao supplyDao;

	/** 保存新增或修改的对象 */
	public void saveSupply(Supply supply) {
		supplyDao.save(supply);
	}

	/** 按id删除对象 */
	public void deleteSupply(Long id) {
		supplyDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteSupply(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		supplyDao.batchExecute("delete Supply t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Supply querySupplyById(Long id) {
		return supplyDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Supply> queryAllSupply() {
		return supplyDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Supply> querySupplyByFilters(final List<PropertyFilter> filters) {
		return supplyDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Supply> querySupplyByFilters(final Page<Supply> page, final List<PropertyFilter> filters) {
		return supplyDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isSupplyUnique(String newSupply, String oldSupply) {
		return supplyDao.isPropertyUnique("name", newSupply, oldSupply);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> querySupplyTreeView(Long parentId) {
		Iterator<Supply> it = supplyDao.querySupplyTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Supply tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			//	node.setText(tmp.getName());

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

	/** 单表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> querySupplyGridView(String query, int pageNo, int pageSize) {

		Page<Supply> page = new Page<Supply>(pageSize);
		page.setPageNo(pageNo);
		page.setOrderBy("id");
		page.setOrder("DESC");
		page = supplyDao.querySupplyGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> querySupplyGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Supply> page = new Page<Supply>(pageSize);
		page.setPageNo(pageNo);
		page = supplyDao.querySupplyGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换对象状态 */
	public void toggleSupplyStatus(Long id, String status) {
		Supply supply = supplyDao.get(id);
		if (status == null) {
			if (supply.getStatus() == null || supply.getStatus().equalsIgnoreCase("0")
					|| supply.getStatus().equalsIgnoreCase("")) {
				supply.setStatus("1");
			} else {
				supply.setStatus("0");
			}
		} else {
			supply.setStatus(status);
		}
		supplyDao.save(supply);
	}

	/** 注入dao对象 */
	@Autowired
	public void setSupplyDao(SupplyDao supplyDao) {
		this.supplyDao = supplyDao;
	}
}
