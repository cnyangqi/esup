/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.savingaccount;

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
import cn.esup.dao.savingaccount.SavingaccountDao;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.entity.savingaccount.Savingaccount;

/**
 * 表主键 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class SavingaccountManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Savingaccount.class);

	/** dao对象 */
	private SavingaccountDao savingaccountDao;

	/** 保存新增或修改的对象 */
	public void saveSavingaccount(Savingaccount savingaccount) {
		savingaccountDao.save(savingaccount);
	}

	/** 按id删除对象 */
	public void deleteSavingaccount(Long id) {
		savingaccountDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteSavingaccount(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		savingaccountDao.batchExecute("delete Savingaccount t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Savingaccount querySavingaccountById(Long id) {
		return savingaccountDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Savingaccount> queryAllSavingaccount() {
		return savingaccountDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Savingaccount> querySavingaccountByFilters(final List<PropertyFilter> filters) {
		return savingaccountDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Savingaccount> querySavingaccountByFilters(final Page<Savingaccount> page,
			final List<PropertyFilter> filters) {
		return savingaccountDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isSavingaccountUnique(String newSavingaccount, String oldSavingaccount) {
		return savingaccountDao.isPropertyUnique("name", newSavingaccount, oldSavingaccount);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> querySavingaccountTreeView(Long parentId) {
		Iterator<Savingaccount> it = savingaccountDao.querySavingaccountTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Savingaccount tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			//	ode.setText(tmp.getName());

			// 当数据字典类型对象的子类型数大于0的时候，表示该类型节点还有子节点，状态为closed
			// Long subTypeNum = tmp.getSubTypeNum();
			// if (subTypeNum != null && subTypeNum > 0) {
			//		node.setState(TreeNode.CLOSED);
			//	} else {
			//		node.setState(TreeNode.OPEN);
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
	public Map<String, Object> querySavingaccountGridView(String query, int pageNo, int pageSize) {

		Page<Savingaccount> page = new Page<Savingaccount>(pageSize);
		page.setPageNo(pageNo);
		page = savingaccountDao.querySavingaccountGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> querySavingaccountGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Savingaccount> page = new Page<Savingaccount>(pageSize);
		page.setPageNo(pageNo);
		page = savingaccountDao.querySavingaccountGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/**设置账户为默认收款账户 */
	public void toggleSavingaccountStatus(Long id) {

		savingaccountDao.initStatus();// 取消当前默认收款账户的默认状态，默认账户唯一性

		Savingaccount savingaccount = savingaccountDao.get(id);
		if (savingaccount.getStatus() == null || savingaccount.getStatus().equalsIgnoreCase("")
				|| savingaccount.getStatus().equalsIgnoreCase("0")) {
			savingaccount.setStatus("1");
		} else {
			savingaccount.setStatus("0");
		}
		savingaccountDao.save(savingaccount);
	}

	/** 注入dao对象 */
	@Autowired
	public void setSavingaccountDao(SavingaccountDao savingaccountDao) {
		this.savingaccountDao = savingaccountDao;
	}
}
