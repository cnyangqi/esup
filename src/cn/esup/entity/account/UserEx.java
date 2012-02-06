package cn.esup.entity.account;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 往spring security session植入用户对象
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class UserEx extends User {
	private static final long serialVersionUID = 1L;
	private cn.esup.entity.account.User user;

	public UserEx(cn.esup.entity.account.User user, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	public cn.esup.entity.account.User getUser() {
		return user;
	}

	public void setUser(cn.esup.entity.account.User user) {
		this.user = user;
	}

}
