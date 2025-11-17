package io.lchangha.logisticsexam.masterdata.domain.vo;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.Uom;
import org.junit.jupiter.api.DisplayName; // DisplayName은 더 이상 사용하지 않지만, 혹시 몰라 남겨둡니다.
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// DisplayName은 클래스 레벨에서만 유지하고, 메서드 레벨에서는 제거합니다.
@DisplayName("Uom 단위 값 객체 테스트")
class UomTest {

    @Test
    void of_팩토리_메서드로_Uom을_성공적으로_생성한다() {
        // given
        String symbol = "kg";

        // when
        Uom uom = Uom.of(symbol);

        // then
        assertThat(uom.symbol()).isEqualTo(symbol);
        assertThat(uom.type()).isEqualTo(Uom.Type.WEIGHT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void of_팩토리_메서드에_비어있거나_공백인_기호를_전달하면_예외가_발생한다(String blankSymbol) {
        // when & then
        assertThatThrownBy(() -> Uom.of(blankSymbol))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("단위 기호는 null이거나 비어 있을 수 없습니다.");
    }

    @Test
    void of_팩토리_메서드에_null_기호를_전달하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> Uom.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("단위 기호는 null이거나 비어 있을 수 없습니다.");
    }

    @Test
    void of_팩토리_메서드에_알_수_없는_기호를_전달하면_예외가_발생한다() {
        // given
        String unknownSymbol = "zzz";

        // when & then
        assertThatThrownBy(() -> Uom.of(unknownSymbol))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("알 수 없는 단위 기호입니다: " + unknownSymbol);
    }

    @Test
    void 생성자로_유효한_Uom을_성공적으로_생성한다() {
        // when
        Uom uom = new Uom("kg", Uom.Type.WEIGHT);

        // then
        assertThat(uom.symbol()).isEqualTo("kg");
        assertThat(uom.type()).isEqualTo(Uom.Type.WEIGHT);
    }

    @Test
    void 생성자에_기호와_타입이_일치하지_않으면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new Uom("kg", Uom.Type.VOLUME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("타입이 일치하지 않습니다.");
    }

    @Test
    void 생성자에_알_수_없는_기호를_전달하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new Uom("zzz", Uom.Type.WEIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("알 수 없는 단위 기호입니다: zzz");
    }
}
