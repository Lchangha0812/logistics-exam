package io.lchangha.logisticsexam.masterdata.domain.vo;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.Measurement;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.Uom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Measurement 값 객체 테스트")
class MeasurementTest {

    @Test
    void Measurement를_성공적으로_생성한다() {
        // given
        BigDecimal value = new BigDecimal("100.0");
        Uom uom = Uom.of("kg");

        // when
        Measurement measurement = new Measurement(value, uom);

        // then
        assertThat(measurement.value()).isEqualByComparingTo(value);
        assertThat(measurement.uom()).isEqualTo(uom);
    }

    @Test
    void 생성자에_null_값을_전달하면_예외가_발생한다() {
        // given
        Uom uom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> new Measurement(null, uom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("측정 값은 null일 수 없습니다.");
    }

    @Test
    void 생성자에_음수_값을_전달하면_예외가_발생한다() {
        // given
        BigDecimal value = new BigDecimal("-10.0");
        Uom uom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> new Measurement(value, uom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("측정 값은 0보다 작을 수 없습니다.");
    }

    @Test
    void 생성자에_null_Uom을_전달하면_예외가_발생한다() {
        // given
        BigDecimal value = new BigDecimal("10.0");

        // when & then
        assertThatThrownBy(() -> new Measurement(value, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("단위는 null일 수 없습니다.");
    }

    @Test
    void isSameType_메서드로_동일_타입_여부를_확인한다() {
        // given
        Measurement m1 = new Measurement(BigDecimal.ONE, Uom.of("kg"));
        Measurement m2 = new Measurement(BigDecimal.TEN, Uom.of("g"));
        Measurement m3 = new Measurement(BigDecimal.ONE, Uom.of("L"));

        // then
        assertThat(m1.isSameType(m2)).isTrue(); // kg (WEIGHT) vs g (WEIGHT)
        assertThat(m1.isSameType(m3)).isFalse(); // kg (WEIGHT) vs L (VOLUME)
        assertThat(m1.isSameType(null)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "1000, g, 1, kg",
            "1, kg, 1000, g",
            "500, ml, 0.5, L",
            "0.5, L, 500, ml"
    })
    void convertWithinSameType_동일_타입_단위로_성공적으로_변환한다(BigDecimal initialValue, String initialUomSymbol,
                                                    BigDecimal expectedValue, String targetUomSymbol) {
        // given
        Measurement initialMeasurement = new Measurement(initialValue, Uom.of(initialUomSymbol));
        Uom targetUom = Uom.of(targetUomSymbol);

        // when
        Measurement convertedMeasurement = initialMeasurement.convertWithinSameType(targetUom);

        // then
        assertThat(convertedMeasurement.value().setScale(5, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedValue.setScale(5, RoundingMode.HALF_UP));
        assertThat(convertedMeasurement.uom()).isEqualTo(targetUom);
    }

    @Test
    void convertWithinSameType_동일한_단위로_변환_시_동일_객체를_반환한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("kg"));

        // when
        Measurement convertedMeasurement = measurement.convertWithinSameType(Uom.of("kg"));

        // then
        assertThat(convertedMeasurement).isSameAs(measurement);
    }

    @Test
    void convertWithinSameType_다른_타입_단위로_변환_시_예외가_발생한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("kg"));
        Uom targetUom = Uom.of("L"); // WEIGHT to VOLUME

        // when & then
        assertThatThrownBy(() -> measurement.convertWithinSameType(targetUom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("단위 종류가 달라 자동 변환이 불가능합니다");
    }

    @Test
    void convertWithinSameType_지원하지_않는_단위로_변환_시_예외가_발생한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("EA")); // COUNT type
        Uom targetUom = Uom.of("BOX"); // COUNT type

        // when & then
        assertThatThrownBy(() -> measurement.convertWithinSameType(targetUom))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("단위 변환을 지원하지 않습니다");
    }

    @Test
    void convertByFactor_명시적_비율로_성공적으로_변환한다() {
        // given
        Measurement initialMeasurement = new Measurement(new BigDecimal("10.0"), Uom.of("L"));
        Uom targetUom = Uom.of("kg");
        BigDecimal factor = new BigDecimal("0.92"); // 1L = 0.92kg

        // when
        Measurement convertedMeasurement = initialMeasurement.convertByFactor(targetUom, factor);

        // then
        assertThat(convertedMeasurement.value()).isEqualByComparingTo(new BigDecimal("9.20"));
        assertThat(convertedMeasurement.uom()).isEqualTo(targetUom);
    }

    @Test
    void convertByFactor_null_targetUom_전달_시_예외가_발생한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("L"));
        BigDecimal factor = BigDecimal.ONE;

        // when & then
        assertThatThrownBy(() -> measurement.convertByFactor(null, factor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환할 단위는 null일 수 없습니다.");
    }

    @Test
    void convertByFactor_null_factor_전달_시_예외가_발생한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("L"));
        Uom targetUom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> measurement.convertByFactor(targetUom, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환 비율은 null일 수 없습니다.");
    }

    @Test
    void convertByFactor_음수_factor_전달_시_예외가_발생한다() {
        // given
        Measurement measurement = new Measurement(BigDecimal.TEN, Uom.of("L"));
        Uom targetUom = Uom.of("kg");
        BigDecimal factor = new BigDecimal("-0.5");

        // when & then
        assertThatThrownBy(() -> measurement.convertByFactor(targetUom, factor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환 비율은 0보다 커야 합니다.");
    }
}
