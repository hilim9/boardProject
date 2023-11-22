package org.koreait.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 엔티티의 변화를 감지하는 클래스
public abstract class Base {

    @CreatedDate // 엔티티가 생성되어 저장될 때 시간 자동 저장
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate // 조회한 엔티티의 값을 변경할 때 시간 자동 저장
    @Column(insertable = false)
    private LocalDateTime modifiedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt; // 삭제 일시
}
