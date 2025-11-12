package io.lchangha.logisticsexam.shared.domain;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * 생성, 수정 감사 정보를 담는 값 객체.
 * @param createdAt 생성 일시
 * @param createdBy 생성자
 * @param lastModifiedAt 마지막 수정 일시
 * @param lastModifiedBy 마지막 수정자
 */
public record AuditInfo(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
) {
    public AuditInfo {
        Assert.notNull(createdAt, "생성 일시는 null일 수 없습니다.");
        Assert.hasText(createdBy, "생성자는 비어 있을 수 없습니다.");
        Assert.notNull(lastModifiedAt, "마지막 수정 일시는 null일 수 없습니다.");
        Assert.hasText(lastModifiedBy, "마지막 수정자는 비어 있을 수 없습니다.");
    }

    /**
     * 생성을 위한 AuditInfo 객체를 생성합니다.
     * @param createdBy 생성자
     * @return 생성 및 수정 정보가 동일한 AuditInfo 객체
     */
    public static AuditInfo forCreation(String createdBy) {
        LocalDateTime now = LocalDateTime.now();
        return new AuditInfo(now, createdBy, now, createdBy);
    }

    /**
     * 수정을 위한 AuditInfo 객체를 생성합니다.
     * 기존 객체의 생성 정보는 유지하고 수정 정보만 현재 시간과 사용자로 업데이트합니다.
     * @param updatedBy 수정자
     * @return 수정 정보가 업데이트된 새로운 AuditInfo 객체
     */
    public AuditInfo forUpdate(String updatedBy) {
        return new AuditInfo(this.createdAt, this.createdBy, LocalDateTime.now(), updatedBy);
    }
}
