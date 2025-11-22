package io.lchangha.logisticsexam.masterdata.domain.vo;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.Quantity;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.Uom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Quantity 값 객체 테스트")
class QuantityTest {

    @Test
    void Quantity를_성공적으로_생성한다() {
        // given
        BigDecimal value = new BigDecimal("100.0");
        Uom uom = Uom.of("kg");

        // when
        Quantity quantity = new Quantity(value, uom);

        // then
        assertThat(quantity.value()).isEqualByComparingTo(value);
        assertThat(quantity.uom()).isEqualTo(uom);
    }

    @Test
    void 생성자에_null_값을_전달하면_예외가_발생한다() {
        // given
        Uom uom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> new Quantity(null, uom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량 값은 필수입니다.");
    }

    @Test
    void 생성자에_음수_값을_전달하면_예외가_발생한다() {
        // given
        BigDecimal value = new BigDecimal("-10.0");
        Uom uom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> new Quantity(value, uom))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("수량은 음수가 될 수 없습니다.");
    }

    @Test
    void 생성자에_null_Uom을_전달하면_예외가_발생한다() {
        // given
        BigDecimal value = new BigDecimal("10.0");

        // when & then
        assertThatThrownBy(() -> new Quantity(value, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량 단위는 필수입니다.");
    }

    @Test
    void isSameType_메서드로_동일_타입_여부를_확인한다() {
        // given
        Quantity m1 = new Quantity(BigDecimal.ONE, Uom.of("kg"));
        Quantity m2 = new Quantity(BigDecimal.TEN, Uom.of("g"));
        Quantity m3 = new Quantity(BigDecimal.ONE, Uom.of("L"));

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
        Quantity initialQuantity = new Quantity(initialValue, Uom.of(initialUomSymbol));
        Uom targetUom = Uom.of(targetUomSymbol);

        // when
        Quantity convertedQuantity = initialQuantity.convertWithinSameType(targetUom);

        // then
        assertThat(convertedQuantity.value().setScale(5, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedValue.setScale(5, RoundingMode.HALF_UP));
        assertThat(convertedQuantity.uom()).isEqualTo(targetUom);
    }

    @Test
    void convertWithinSameType_동일한_단위로_변환_시_동일_객체를_반환한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("kg"));

        // when
        Quantity convertedQuantity = quantity.convertWithinSameType(Uom.of("kg"));

        // then
        assertThat(convertedQuantity).isSameAs(quantity);
    }

    @Test
    void convertWithinSameType_다른_타입_단위로_변환_시_예외가_발생한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("kg"));
        Uom targetUom = Uom.of("L"); // WEIGHT to VOLUME

        // when & then
        assertThatThrownBy(() -> quantity.convertWithinSameType(targetUom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("단위 종류가 달라 자동 변환이 불가능합니다");
    }

    @Test
    void convertWithinSameType_지원하지_않는_단위로_변환_시_예외가_발생한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("EA")); // COUNT type
        Uom targetUom = Uom.of("BOX"); // COUNT type

        // when & then
        assertThatThrownBy(() -> quantity.convertWithinSameType(targetUom))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("단위 변환을 지원하지 않습니다");
    }

    @Test
    void convertByFactor_명시적_비율로_성공적으로_변환한다() {
        // given
        Quantity initialQuantity = new Quantity(new BigDecimal("10.0"), Uom.of("L"));
        Uom targetUom = Uom.of("kg");
        BigDecimal factor = new BigDecimal("0.92"); // 1L = 0.92kg

        // when
        Quantity convertedQuantity = initialQuantity.convertByFactor(targetUom, factor);

        // then
        assertThat(convertedQuantity.value()).isEqualByComparingTo(new BigDecimal("9.20"));
        assertThat(convertedQuantity.uom()).isEqualTo(targetUom);
    }

    @Test
    void convertByFactor_null_targetUom_전달_시_예외가_발생한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("L"));
        BigDecimal factor = BigDecimal.ONE;

        // when & then
        assertThatThrownBy(() -> quantity.convertByFactor(null, factor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환할 단위는 null일 수 없습니다.");
    }

    @Test
    void convertByFactor_null_factor_전달_시_예외가_발생한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("L"));
        Uom targetUom = Uom.of("kg");

        // when & then
        assertThatThrownBy(() -> quantity.convertByFactor(targetUom, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환 비율은 null일 수 없습니다.");
    }

    @Test
    void convertByFactor_음수_factor_전달_시_예외가_발생한다() {
        // given
        Quantity quantity = new Quantity(BigDecimal.TEN, Uom.of("L"));
        Uom targetUom = Uom.of("kg");
        BigDecimal factor = new BigDecimal("-0.5");

        // when & then
        assertThatThrownBy(() -> quantity.convertByFactor(targetUom, factor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변환 비율은 0보다 커야 합니다.");
    }
}
