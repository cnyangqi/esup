/**
 * Copyright 2011 iThinkGo, Inc. All rights reserved.
 */

package cn.esup.entity.jeasyui;

import java.util.List;
import java.util.Map;

/**
 * jeasyui tree entity
 * 
 * @author <a href= "mailto:qi.yang.cn@gmail.com"> qi.yang.cn@gmail.com </a>
 */
public class TreeNode {
	public static String OPEN = "open";
	public static String CLOSED = "closed";

	private Long id;
	private String text;
	private String state;
	private String checked;
	private Map<String, String> attributes;
	private List<TreeNode> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
