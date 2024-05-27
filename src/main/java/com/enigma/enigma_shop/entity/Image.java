package com.enigma.enigma_shop.entity;


import com.enigma.enigma_shop.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.IMAGE_PRODUCT)
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "path", nullable = false)
	// path = /Users/idazanggara/Documents/Gambar Enigma Shop/FileName
	private String path;

	@Column(name = "size", nullable = false)
	private Long size;

	@Column(name = "content_type", nullable = false)
	private String contentType;
}
