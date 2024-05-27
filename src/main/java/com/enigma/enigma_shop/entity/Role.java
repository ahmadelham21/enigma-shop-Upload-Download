package com.enigma.enigma_shop.entity;

import com.enigma.enigma_shop.constant.ConstantTable;
import com.enigma.enigma_shop.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER_ROLE)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;
}
