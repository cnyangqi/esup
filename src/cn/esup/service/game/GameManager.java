/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */
package cn.esup.service.game;

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
import cn.esup.dao.game.GameDao;
import cn.esup.entity.game.Game;
import cn.esup.entity.jeasyui.TreeNode;

/**
 * ID 管理
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Service
@Transactional
public class GameManager {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(Game.class);

	/** dao对象 */
	private GameDao gameDao;

	/** 保存新增或修改的对象 */
	public void saveGame(Game game) {
		gameDao.save(game);
	}

	/** 按id删除对象 */
	public void deleteGame(Long id) {
		gameDao.delete(id);
	}

	/** 按ids批量删除对象 ids是多个id拼接的字符串，id之间分割符与前台约定为',' */
	public void batchDeleteGame(String ids) {
		Map<String, Long[]> map = new HashMap<String, Long[]>();
		map.put("ids", SS3Tools.ids2LongArray(ids));
		gameDao.batchExecute("delete Game t where t.id in (:ids)", map);// 命名参数
	}

	/** 按id获取对象 */
	@Transactional(readOnly = true)
	public Game queryGameById(Long id) {
		return gameDao.get(id);
	}

	/** 获取全部对象 */
	@Transactional(readOnly = true)
	public List<Game> queryAllGame() {
		return gameDao.getAll();
	}

	/** 按属性过滤条件查找所有对象 */
	@Transactional(readOnly = true)
	public List<Game> queryGameByFilters(final List<PropertyFilter> filters) {
		return gameDao.find(filters);
	}

	/** 按属性过滤条件列表分页查找对象 */
	@Transactional(readOnly = true)
	public Page<Game> queryGameByFilters(final Page<Game> page, final List<PropertyFilter> filters) {
		return gameDao.findPage(page, filters);
	}

	/** 判断对象的属性值在数据库内是否唯一 */
	@Transactional(readOnly = true)
	public boolean isGameUnique(String newGame, String oldGame) {
		return gameDao.isPropertyUnique("name", newGame, oldGame);
	}

	/** 查询对象树视图 */
	@Transactional(readOnly = true)
	public List<TreeNode> queryGameTreeView(Long parentId) {
		Iterator<Game> it = gameDao.queryGameTreeView(parentId).iterator();
		List<TreeNode> list = new LinkedList<TreeNode>();
		while (it.hasNext()) {
			Game tmp = it.next();
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
	public Map<String, Object> queryGameGridView(String query, int pageNo, int pageSize) {

		Page<Game> page = new Page<Game>(pageSize);
		page.setPageNo(pageNo);
		page = gameDao.queryGameGridView(query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 主子表查询对象列表视图  */
	@Transactional(readOnly = true)
	public Map<String, Object> queryGameGridView(Long typeId, String query, int pageNo, int pageSize) {

		Page<Game> page = new Page<Game>(pageSize);
		page.setPageNo(pageNo);
		page = gameDao.queryGameGridView(typeId, query, page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalCount());
		map.put("rows", page.getResult());

		return map;
	}

	/** 切换对象状态 */
	public void toggleGameStatus(Long id) {
		Game game = gameDao.get(id);
		//	if (game.getStatus().equalsIgnoreCase("1")) {
		//		game.setStatus("0");
		//	} else {
		//		game.setStatus("1");
		//	}
		gameDao.save(game);
	}

	/** 注入dao对象 */
	@Autowired
	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}
}
