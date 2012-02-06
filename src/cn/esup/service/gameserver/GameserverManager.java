/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.gameserver;

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
import cn.esup.dao.gameserver.GameserverDao;
import cn.esup.entity.gameserver.Gameserver;
import cn.esup.entity.jeasyui.TreeNode;

/**
 *  管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class GameserverManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Gameserver.class);

	/** dao对象 */
	private GameserverDao gameserverDao;

	/** 保存新增或修改的对象 */
	public void saveGameserver(Gameserver gameserver) {
		gameserverDao.save(gameserver);
	}

	/** 按id删除对象 */
	public void deleteGameserver(Long id) {
		gameserverDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteGameserver(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		gameserverDao.batchExecute("delete Gameserver t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Gameserver queryGameserverById(Long id) {
		return gameserverDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Gameserver> queryAllGameserver() {
		return gameserverDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Gameserver> queryGameserverByFilters(final List<PropertyFilter> filters) {
		return gameserverDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Gameserver> queryGameserverByFilters(final Page<Gameserver> page, final List<PropertyFilter> filters) {
		return gameserverDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isGameserverUnique(String newGameserver, String oldGameserver) {
		return gameserverDao.isPropertyUnique("name", newGameserver, oldGameserver);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryGameserverTreeView(Long parentId) {
		Iterator<Gameserver> it = gameserverDao.queryGameserverTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Gameserver tmp = it.next();
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
	public Map<String, Object> queryGameserverGridView(String query, int pageNo, int pageSize) {

		Page<Gameserver> page = new Page<Gameserver>(pageSize);
		page.setPageNo(pageNo);
		page = gameserverDao.queryGameserverGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryGameserverGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Gameserver> page = new Page<Gameserver>(pageSize);
		page.setPageNo(pageNo);
		page = gameserverDao.queryGameserverGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换对象状态 */
	public void toggleGameserverStatus(Long id) {
		Gameserver gameserver = gameserverDao.get(id);
		//	if (gameserver.getStatus().equalsIgnoreCase("1")) {
		//		gameserver.setStatus("0");
		//	} else {
		//		gameserver.setStatus("1");
		//	}
		gameserverDao.save(gameserver);
	}

	/** 注入dao对象 */
	@Autowired
	public void setGameserverDao(GameserverDao gameserverDao) {
		this.gameserverDao = gameserverDao;
	}
}
