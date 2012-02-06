/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.receive;

import java.util.Date;
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
import cn.esup.dao.receive.ReceiveDao;
import cn.esup.entity.jeasyui.TreeNode;
import cn.esup.entity.receive.Receive;

/**
 * 主键 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class ReceiveManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Receive.class);

	/** dao对象 */
	private ReceiveDao receiveDao;

	/** 保存新增或修改的对象 */
	public void saveReceive(Receive receive) {
		receiveDao.save(receive);
	}

	/** 按id删除对象 */
	public void deleteReceive(Long id) {
		receiveDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteReceive(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		receiveDao.batchExecute("delete Receive t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Receive queryReceiveById(Long id) {
		return receiveDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Receive> queryAllReceive() {
		return receiveDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Receive> queryReceiveByFilters(final List<PropertyFilter> filters) {
		return receiveDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Receive> queryReceiveByFilters(final Page<Receive> page, final List<PropertyFilter> filters) {
		return receiveDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isReceiveUnique(String newReceive, String oldReceive) {
		return receiveDao.isPropertyUnique("name", newReceive, oldReceive);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryReceiveTreeView(Long parentId) {
		Iterator<Receive> it = receiveDao.queryReceiveTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Receive tmp = it.next();
			TreeNode node = new TreeNode();
			node.setId(tmp.getId());
			//node.setText(tmp.getName());

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
	public Map<String, Object> queryReceiveGridView(String query, int pageNo, int pageSize) {

		Page<Receive> page = new Page<Receive>(pageSize);
		page.setPageNo(pageNo);
		page.setOrderBy("id");
		page.setOrder("DESC");

		page = receiveDao.queryReceiveGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryReceiveGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Receive> page = new Page<Receive>(pageSize);
		page.setPageNo(pageNo);
		page = receiveDao.queryReceiveGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换对象状态 */
	public void toggleReceiveStatus(Long id) {
		Receive receive = receiveDao.get(id);
		receive.setPublicDate(new Date());

		if (receive.getStatus() == null || receive.getStatus().equalsIgnoreCase("0")) {
			receive.setStatus("1");
		} else {
			receive.setStatus("0");
		}
		receiveDao.save(receive);
	}

	/** 查询审核通过的最后10条收货单记录 */
	public List<Receive> quertTop10Record() {
		return receiveDao.quertTop10Record();
	}

	/** 注入dao对象 */
	@Autowired
	public void setReceiveDao(ReceiveDao receiveDao) {
		this.receiveDao = receiveDao;
	}
}
