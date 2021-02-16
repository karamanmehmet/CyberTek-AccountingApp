package com.cybertek.accounting.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "insertDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime insertDateTime;

	private String insertUserId;
	
	@Column(name = "lastUpdateDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDateTime;
	
	private String lastUpdateUserId;


	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;

		if (!Objects.equals(getClass(), o.getClass())) {
			return false;
		}

		BaseEntity that = (BaseEntity) o;
		return this.id != null && Objects.equals(this.id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public BaseEntity(long id, LocalDateTime insertDateTime, String insertUserId, LocalDateTime lastUpdateDateTime,
					  String lastUpdateUserId) {
		super();
		this.id = id;
		this.insertDateTime = insertDateTime;
		this.insertUserId = insertUserId;
		this.lastUpdateDateTime = lastUpdateDateTime;
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public BaseEntity() {
	}

}
