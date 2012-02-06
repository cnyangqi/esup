/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
 package cn.esup.service.payrecords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;

import cn.esup.entity.payrecords.Payrecords;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.dao.payrecords.PayrecordsDao;
import cn.esup.service.payrecords.PayrecordsManager;
import cn.esup.common.SS3Tools;

/**
 * 主键 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class PayrecordsManager{
	
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Payrecords.class);
	
	/** dao对象 */
	private PayrecordsDao payrecordsDao;
	
	/** 保存新增或修改的对象 */
	public void savePayrecords(Payrecords payrecords) {
		payrecordsDao.save(payrecords);
	}

	/** 按id删除对象 */
	public void deletePayrecords(Long id) {
		payrecordsDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeletePayrecords(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		payrecordsDao.batchExecute("delete Payrecords t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Payrecords queryPayrecordsById(Long id) {
		return payrecordsDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Payrecords> queryAllPayrecords() {
		return payrecordsDao.getAll();
	}
	
	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Payrecords> queryPayrecordsByFilters(final List<PropertyFilter> filters) {
		return payrecordsDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Payrecords> queryPayrecordsByFilters(final Page<Payrecords> page,
			final List<PropertyFilter> filters) {
		return payrecordsDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isPayrecordsUnique(String newPayrecords, String oldPayrecords) {
		return payrecordsDao.isPropertyUnique("name", newPayrecords, oldPayrecords);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryPayrecordsTreeView(Long parentId) {
		Iterator<Payrecords> it = payrecordsDao.queryPayrecordsTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Payrecords tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			//	node.setText(tmp.getName());

			//	当数据字典类型对象的子类型数大于0的时候，表示该类型节点还有子节点，状态为closed
			//	Long subTypeNum = tmp.getSubTypeNum();
			//	if (subTypeNum != null && subTypeNum > 0) {
			//	node.setState(TreeNode.CLOSED);
			//	} else {
			//	node.setState(TreeNode.OPEN);
			//	}

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
	public Map<String, Object> queryPayrecordsGridView(String query, int pageNo, int pageSize) {

		Page<Payrecords> page = new Page<Payrecords>(pageSize);
		page.setPageNo(pageNo);
		page = payrecordsDao.queryPayrecordsGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}
	
	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryPayrecordsGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Payrecords> page = new Page<Payrecords>(pageSize);
		page.setPageNo(pageNo);
		page = payrecordsDao.queryPayrecordsGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}
	
	/** 切换对象状态 */
	public void togglePayrecordsStatus(Long id, String status) {
		Payrecords payrecords = payrecordsDao.get(id);
		if (status == null) {
			//	if (payrecords.getStatus() == null || payrecords.getStatus().equalsIgnoreCase("0") || payrecords.getStatus().equalsIgnoreCase("")) {
			//		payrecords.setStatus("1");
			//	} else {
			//		payrecords.setStatus("0");
			//	}
		} else {
			//	payrecords.setStatus(status);
		}
		payrecordsDao.save(payrecords);
	}
	
	/** 注入dao对象 */
	@Autowired
	public void setPayrecordsDao(PayrecordsDao payrecordsDao){
		this.payrecordsDao = payrecordsDao;
	}
}
