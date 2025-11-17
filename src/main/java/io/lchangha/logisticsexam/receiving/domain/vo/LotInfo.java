package io.lchangha.logisticsexam.receiving.domain.vo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 로트 번호와 유통기한의 쌍을 나타내는 값 객체(Value Object)입니다.
 * 이 정보는 입고 라인과 함께 이동합니다.
 *
 * @param lotCode    로트 번호
 * @param expiryDate 유통기한
 */
public record LotInfo(
        String lotCode,
        LocalDate expiryDate
) {

    public LotInfo {
        if (lotCode != null && lotCode.isBlank()) {
            lotCode = null;
        }
    }

    /**
     * 로트 정보가 비어있는지 (로트 번호와 유통기한 모두 없는지) 확인합니다.
     *
     * @return 정보가 비어있으면 true
     */
    public boolean isEmpty() {
        return lotCode == null && expiryDate == null;
    }

    /**
     * 현재 로트 정보에 다른 로트 정보를 병합합니다.
     * <p>
     * 덮어쓰려는 정보(`override`)의 필드가 null이 아닌 경우에만 현재 정보를 대체합니다.
     *
     * @param override 덮어쓸 로트 정보
     * @return 병합된 새로운 LotInfo 객체
     */
        public LotInfo merge(LotInfo override) {
            if (override == null || override.isEmpty()) {
                return this;
            }
            return new LotInfo(
                    override.lotCode != null ? override.lotCode : this.lotCode,
                    override.expiryDate != null ? override.expiryDate : this.expiryDate
            );
        }
    
        /**
         * 다른 로트 정보와 내용이 일치하는지 확인합니다.
         *
         * @param other 비교할 다른 로트 정보
         * @return 로트 번호와 유통기한이 모두 일치하면 true
         */
        public boolean matches(LotInfo other) {
            if (other == null) {
                return false;
            }
            return Objects.equals(lotCode, other.lotCode) &&
                    Objects.equals(expiryDate, other.expiryDate);
        }
    }
    