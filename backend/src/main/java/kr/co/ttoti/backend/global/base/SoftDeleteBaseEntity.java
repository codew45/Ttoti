package kr.co.ttoti.backend.global.base;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class SoftDeleteBaseEntity {

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public void updateDeletedAt() {
		if (deletedAt == null) {
			deletedAt = LocalDateTime.now();
		}
	}
}
