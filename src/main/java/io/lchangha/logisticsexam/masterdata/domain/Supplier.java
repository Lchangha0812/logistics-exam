package io.lchangha.logisticsexam.masterdata.domain;

import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Supplier {
    public enum Status {ACTIVE, INACTIVE}
    private Long id;
    private String name;
    private Integer leadTimeDays;
    private Status status;
    private String address;
    private String phoneNumber;
    private String email;
    private String contactPersonName;
    private String businessRegistrationNumber;
    private String paymentTerms;

    private AuditInfo auditInfo;



    @Builder
    private Supplier(Long id, String name, Integer leadTimeDays, Status status,
                     String address, String phoneNumber, String email, String contactPersonName,
                     String businessRegistrationNumber, String paymentTerms, AuditInfo auditInfo) {

        Assert.hasText(name, "공급사 이름은 비어 있을 수 없습니다.");
        Assert.notNull(leadTimeDays, "리드 타임은 null일 수 없습니다.");
        Assert.isTrue(leadTimeDays >= 0, "리드 타임은 0보다 작을 수 없습니다.");
        Assert.notNull(status, "공급사 상태는 null일 수 없습니다.");
        Assert.hasText(businessRegistrationNumber, "사업자 등록 번호는 비어 있을 수 없습니다.");
        Assert.notNull(auditInfo, "감사 정보는 null일 수 없습니다.");

        this.id = id;
        this.name = name;
        this.leadTimeDays = leadTimeDays;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.contactPersonName = contactPersonName;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.paymentTerms = paymentTerms;
        this.auditInfo = auditInfo;
    }

    /**
     * 이 공급사가 주어진 리드 타임 내에 주문을 이행할 수 있는지 확인합니다.
     * 공급사의 상태가 ACTIVE여야 하며, 공급사의 리드 타임이 요구된 리드 타임보다 짧거나 같아야 합니다.
     * @param requiredLeadTimeDays 요구되는 최대 리드 타임 (일)
     * @return 주문 이행이 가능하면 true, 불가능하면 false
     */
    public boolean canFulfillOrder(int requiredLeadTimeDays) {
        if (this.status != Status.ACTIVE) {
            return false;
        }

        if (this.leadTimeDays > requiredLeadTimeDays) {
            return false;
        }

        return true;
    }
}
