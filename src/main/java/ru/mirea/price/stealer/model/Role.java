package ru.mirea.price.stealer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "role")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;
}
