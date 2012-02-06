/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
 package cn.esup.service.payable;

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

import cn.esup.entity.payable.Payable;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.dao.payable.PayableDao;
import cn.esup.service.payable.PayableManager;
import cn.esup.common.SS3Tools;

/**
 * 主键 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class PayableManager{
	
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Payable.class);
	
	/** dao对象 */
	private PayableDao payableDao;
	
	/** 保存新增或修改的对象 */
	public void savePayable(Payable payable) {
		payableDao.save(payable);
	}

	/** 按id删除对象 */
	public void deletePayable(Long id) {
		payableDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeletePayable(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		payableDao.batchExecute("delete Payable t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Payable queryPayableById(Long id) {
		return payableDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Payable> queryAllPayable() {
		return payableDao.getAll();
	}
	
	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Payable> queryPayableByFilters(final List<PropertyFilter> filters) {
		return payableDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Payable> queryPayableByFilters(final Page<Payable> page,
			final List<PropertyFilter> filters) {
		return payableDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isPayableUnique(String newPayable, String oldPayable) {
		return payableDao.isPropertyUnique("name", newPayable, oldPayable);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryPayableTreeView(Long parentId) {
		Iterator<Payable> it = payableDao.queryPayableTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Payable tmp = it.next();
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
	public Map<String, Object> queryPayableGridView(String query, int pageNo, int pageSize) {

		Page<Payable> page = new Page<Payable>(pageSize);
		page.setPageNo(pageNo);
		page = payableDao.queryPayableGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}
	
	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryPayableGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Payable> page = new Page<Payable>(pageSize);
		page.setPageNo(pageNo);
		page = payableDao.queryPayableGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}
	
	/** 切换对象状态 */
	public void togglePayableStatus(Long id, String status) {
		Payable payable = payableDao.get(id);
		if (status == null) {
			//	if (payable.getStatus() == null || payable.getStatus().equalsIgnoreCase("0") || payable.getStatus().equalsIgnoreCase("")) {
			//		payable.setStatus("1");
			//	} else {
			//		payable.setStatus("0");
			//	}
		} else {
			//	payable.setStatus(status);
		}
		payableDao.save(payable);
	}
	
	/** 注入dao对象 */
	@Autowired
	public void setPayableDao(PayableDao payableDao){
		this.payableDao = payableDao;
	}
}
