/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.game;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.esup.entity.IdEntity;

/**
 * ID 
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Game extends IdEntity {

	//所有属性

	/** 游戏名 */
	private String gamename;
	/** 排序首字母 */
	private String firstcode;
	/** 是否热门 */
	private String ishot;
	/** 可见状态 */
	private String visible;

	public String getGamename() {
		return this.gamename;
	}

	public void setGamename(String value) {
		this.gamename = value;
	}

	public String getFirstcode() {
		return this.firstcode;
	}

	public void setFirstcode(String value) {
		this.firstcode = value;
	}

	public String getIshot() {
		return this.ishot;
	}

	public void setIshot(String value) {
		this.ishot = value;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String value) {
		this.visible = value;
	}

}
