package com.enigma.enigma_shop.entity;

import com.enigma.enigma_shop.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER_ACCOUNT)
public class UserAccount implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_enable")
	private Boolean isEnable;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> role;


	@Override
	// GrantedAuthority{
//		authorities: ROLE_CUSTOMER;
	//}
	// GrantedAuthority{
//		authorities: ROLE_ADMIN;
	//}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();
	}

	@Override
	public String getPassword(){
		return password;
	}

	@Override
	public String getUsername(){
		return username;
		// kalau pakai email, disini return email
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnable;
	}
}
