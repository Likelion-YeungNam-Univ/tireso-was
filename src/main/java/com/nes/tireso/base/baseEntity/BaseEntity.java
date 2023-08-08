package com.nes.tireso.base.baseEntity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
}