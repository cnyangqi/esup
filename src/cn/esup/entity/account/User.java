package cn.esup.entity.account;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.modules.utils.reflection.ConvertUtils;

import cn.esup.entity.IdEntity;

import com.google.common.collect.Lists;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系. 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 
 * @author yanqg
 */
@Entity
// 表名与类名不相同时重新定义表名.
@Table(name = "ACCT_USER")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends IdEntity {

	/** 登录名 */
	private String loginName;
	/** 登录密码 */
	private String password;
	/** 用户姓名 */
	private String name;
	/** 用户昵称 */
	private String nickname;
	/** 手机号码 */
	private String mobi;
	/** 联系电话 */
	private String tel;
	/** QQ号码 */
	private String qq;
	/** 电子邮件 */
	private String email;
	/** 找回密码问题ID */
	private String questionId;
	/** 找回密码答案 */
	private String answer;
	/** 账户状态 0禁用/1激活 */
	private String status;
	/** 扩展字段1 */
	private String extendField1;
	/** 扩展字段2 */
	private String extendField2;
	/** 扩展字段3 */
	private String extendField3;
	/** 扩展字段4 */
	private String extendField4;
	/** 扩展字段5 */
	private String extendField5;
	/** 扩展字段6 */
	private String extendField6;
	/** 扩展字段7 */
	private String extendField7;
	/** 扩展字段8 */
	private String extendField8;
	/** 扩展字段9 */
	private String extendField9;
	/** 扩展字段10 */
	private String extendField10;

	private List<Role> roleList = Lists.newArrayList();// 有序的关联对象集合

	// 字段非空且唯一, 用于提醒Entity使用者及生成DDL.
	@Column(nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobi() {
		return mobi;
	}

	public void setMobi(String mobi) {
		this.mobi = mobi;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtendField1() {
		return extendField1;
	}

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	public String getExtendField2() {
		return extendField2;
	}

	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}

	public String getExtendField3() {
		return extendField3;
	}

	public void setExtendField3(String extendField3) {
		this.extendField3 = extendField3;
	}

	public String getExtendField4() {
		return extendField4;
	}

	public void setExtendField4(String extendField4) {
		this.extendField4 = extendField4;
	}

	public String getExtendField5() {
		return extendField5;
	}

	public void setExtendField5(String extendField5) {
		this.extendField5 = extendField5;
	}

	public String getExtendField6() {
		return extendField6;
	}

	public void setExtendField6(String extendField6) {
		this.extendField6 = extendField6;
	}

	public String getExtendField7() {
		return extendField7;
	}

	public void setExtendField7(String extendField7) {
		this.extendField7 = extendField7;
	}

	public String getExtendField8() {
		return extendField8;
	}

	public void setExtendField8(String extendField8) {
		this.extendField8 = extendField8;
	}

	public String getExtendField9() {
		return extendField9;
	}

	public void setExtendField9(String extendField9) {
		this.extendField9 = extendField9;
	}

	public String getExtendField10() {
		return extendField10;
	}

	public void setExtendField10(String extendField10) {
		this.extendField10 = extendField10;
	}

	// 多对多定义
	@ManyToMany
	// 中间表定义,表名采用默认命名规则
	@JoinTable(name = "ACCT_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序.
	@OrderBy("id")
	// 集合中对象id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	// 非持久化属性.
	@Transient
	public String getRoleNames() {
		return ConvertUtils.convertElementPropertyToString(roleList, "name", ",");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */
	// 非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ConvertUtils.convertElementPropertyToList(roleList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}