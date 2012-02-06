/**
 * Copyright 2011-2012 yangq, Inc. All rights reserved.
 */

package cn.esup.entity.gamearea;

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
@Table(name = "gamearea")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Gamearea extends IdEntity {

	private Long gameid;
	private String areaname;
	private String visible;

	public Gamearea() {
	}

	public Long getGameid() {
		return gameid;
	}

	public void setGameid(Long gameid) {
		this.gameid = gameid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getVisible() {
		return this.visible;
	}

	public void setVisible(String value) {
		this.visible = value;
	}

}
