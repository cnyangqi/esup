/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.gamearea;

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
import cn.esup.dao.gamearea.GameareaDao;
import cn.esup.entity.gamearea.Gamearea;
import cn.esup.entity.jeasyui.TreeNode;

/**
 * ID 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class GameareaManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Gamearea.class);

	/** dao对象 */
	private GameareaDao gameareaDao;

	/** 保存新增或修改的对象 */
	public void saveGamearea(Gamearea gamearea) {
		gameareaDao.save(gamearea);
	}

	/** 按id删除对象 */
	public void deleteGamearea(Long id) {
		gameareaDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteGamearea(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		gameareaDao.batchExecute("delete Gamearea t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Gamearea queryGameareaById(Long id) {
		return gameareaDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Gamearea> queryAllGamearea() {
		return gameareaDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Gamearea> queryGameareaByFilters(final List<PropertyFilter> filters) {
		return gameareaDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Gamearea> queryGameareaByFilters(final Page<Gamearea> page, final List<PropertyFilter> filters) {
		return gameareaDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isGameareaUnique(String newGamearea, String oldGamearea) {
		return gameareaDao.isPropertyUnique("name", newGamearea, oldGamearea);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryGameareaTreeView(Long parentId) {
		Iterator<Gamearea> it = gameareaDao.queryGameareaTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Gamearea tmp = it.next();
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
	public Map<String, Object> queryGameareaGridView(String query, int pageNo, int pageSize) {

		Page<Gamearea> page = new Page<Gamearea>(pageSize);
		page.setPageNo(pageNo);
		page = gameareaDao.queryGameareaGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryGameareaGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Gamearea> page = new Page<Gamearea>(pageSize);
		page.setPageNo(pageNo);
		page = gameareaDao.queryGameareaGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换对象状态 */
	public void toggleGameareaStatus(Long id) {
		Gamearea gamearea = gameareaDao.get(id);
		//	if (gamearea.getStatus().equalsIgnoreCase("1")) {
		//		gamearea.setStatus("0");
		//	} else {
		//		gamearea.setStatus("1");
		//	}
		gameareaDao.save(gamearea);
	}

	/** 注入dao对象 */
	@Autowired
	public void setGameareaDao(GameareaDao gameareaDao) {
		this.gameareaDao = gameareaDao;
	}
}
