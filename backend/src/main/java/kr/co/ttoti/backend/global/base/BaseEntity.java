package kr.co.ttoti.backend.global.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "modified_at")
    private Instant modifiedAt;

}
