package io.lchangha.logisticsexam.masterdata.domain;

import io.lchangha.logisticsexam.masterdata.item.domain.Item;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.*;
import io.lchangha.logisticsexam.masterdata.vo.TemperatureZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Item 도메인 객체 테스트")
class ItemTest {

    private final Uom kg = Uom.of("kg");
    private final Uom g = Uom.of("g");
    private final Uom L = Uom.of("L");
    private final Uom EA = Uom.of("EA");
    private final Uom BOX = Uom.of("BOX");

    private Item createTestItem(Uom baseUom, UomConversionProfile profile) {
        return Item.builder()
                .id(1L)
                .name("Test Item")
                .sku(new SKU("SKU001"))
                .baseUom(baseUom)
                .itemCategory(ItemCategory.PRODUCE)
                .temperatureZone(TemperatureZone.AMBIENT)
                .requiresExpiry(false)
                .safetyStock(BigDecimal.TEN)
                .active(true)
                .uomConversionProfile(profile)
                .build();
    }

    @Test
    void Item을_성공적으로_생성한다() {
        // given
        Uom baseUom = EA;
        UomConversionProfile profile = new UomConversionProfile(Collections.emptySet());

        // when
        Item item = createTestItem(baseUom, profile);

        // then
        assertThat(item).isNotNull();
        assertThat(item.getBaseUom()).isEqualTo(baseUom);
        assertThat(item.getUomConversionProfile()).isEqualTo(profile);
    }

    @Test
    void 생성자에_null_이름을_전달하면_예외가_발생한다() {
        // given
        Uom baseUom = EA;
        UomConversionProfile profile = new UomConversionProfile(Collections.emptySet());

        // when & then
        assertThatThrownBy(() -> Item.builder()
                .id(1L)
                .name(null)
                .sku(new SKU("SKU001"))
                .baseUom(baseUom)
                .itemCategory(ItemCategory.PRODUCE)
                .temperatureZone(TemperatureZone.AMBIENT)
                .requiresExpiry(false)
                .safetyStock(BigDecimal.TEN)
                .active(true)
                .uomConversionProfile(profile)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이템 이름은 비어 있을 수 없습니다.");
    }

    @Test
    void 생성자에_null_SKU를_전달하면_예외가_발생한다() {
        // given
        Uom baseUom = EA;
        UomConversionProfile profile = new UomConversionProfile(Collections.emptySet());

        // when & then
        assertThatThrownBy(() -> Item.builder()
                .id(1L)
                .name("Test Item")
                .sku(null)
                .baseUom(baseUom)
                .itemCategory(ItemCategory.PRODUCE)
                .temperatureZone(TemperatureZone.AMBIENT)
                .requiresExpiry(false)
                .safetyStock(BigDecimal.TEN)
                .active(true)
                .uomConversionProfile(profile)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이템 SKU는 null일 수 없습니다.");
    }

    @Test
    void 생성자에_null_baseUom을_전달하면_예외가_발생한다() {
        // given
        UomConversionProfile profile = new UomConversionProfile(Collections.emptySet());

        // when & then
        assertThatThrownBy(() -> Item.builder()
                .id(1L)
                .name("Test Item")
                .sku(new SKU("SKU001"))
                .baseUom(null)
                .itemCategory(ItemCategory.PRODUCE)
                .temperatureZone(TemperatureZone.AMBIENT)
                .requiresExpiry(false)
                .safetyStock(BigDecimal.TEN)
                .active(true)
                .uomConversionProfile(profile)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이템 기본 단위(baseUom)는 null일 수 없습니다.");
    }

    @Test
    void 생성자에_null_UomConversionProfile을_전달하면_예외가_발생한다() {
        // given
        Uom baseUom = EA;

        // when & then
        assertThatThrownBy(() -> Item.builder()
                .id(1L)
                .name("Test Item")
                .sku(new SKU("SKU001"))
                .baseUom(baseUom)
                .itemCategory(ItemCategory.PRODUCE)
                .temperatureZone(TemperatureZone.AMBIENT)
                .requiresExpiry(false)
                .safetyStock(BigDecimal.TEN)
                .active(true)
                .uomConversionProfile(null)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이템 단위 변환 프로필은 null일 수 없습니다.");
    }

    @Test
    void convert_동일한_단위로_변환_시_동일_객체를_반환한다() {
        // given
        Item item = createTestItem(kg, new UomConversionProfile(Collections.emptySet()));
        Quantity quantity = new Quantity(BigDecimal.TEN, kg);

        // when
        Quantity converted = item.convert(quantity, kg);

        // then
        assertThat(converted).isSameAs(quantity);
    }

    @Test
    void convert_동일_타입_내_보편적_단위로_성공적으로_변환한다() {
        // given
        Item item = createTestItem(kg, new UomConversionProfile(Collections.emptySet()));
        Quantity quantity = new Quantity(new BigDecimal("1.5"), kg); // 1.5 kg

        // when
        Quantity converted = item.convert(quantity, g); // to g

        // then
        assertThat(converted.value()).isEqualByComparingTo(new BigDecimal("1500.0"));
        assertThat(converted.uom()).isEqualTo(g);
    }

    @Test
    void convert_COUNT_타입_단위로_아이템별_변환을_성공적으로_수행한다() {
        // given
        // baseUom: EA, 1 BOX = 12 EA
        UomConversionProfile profile = new UomConversionProfile(Set.of(
                new UomConversionFactor(BOX, new BigDecimal("12")) // 1 BOX = 12 EA
        ));
        Item item = createTestItem(EA, profile);
        Quantity quantity = new Quantity(new BigDecimal("2"), BOX); // 2 BOX

        // when
        Quantity converted = item.convert(quantity, EA); // to EA

        // then
        assertThat(converted.value()).isEqualByComparingTo(new BigDecimal("24"));
        assertThat(converted.uom()).isEqualTo(EA);
    }

    @Test
    void convert_다른_타입_단위로_아이템별_변환을_성공적으로_수행한다() {
        // given
        // baseUom: EA, 1 L = 1 EA, 1 KG = 0.92 EA (즉, 1 EA = 1.0869565 KG)
        UomConversionProfile profile = new UomConversionProfile(Set.of(
                new UomConversionFactor(L, BigDecimal.ONE), // 1 L = 1 EA
                new UomConversionFactor(kg, new BigDecimal("0.92")) // 1 KG = 0.92 EA
        ));
        Item item = createTestItem(EA, profile);
        Quantity quantity = new Quantity(new BigDecimal("10"), L); // 10 L

        // when
        Quantity converted = item.convert(quantity, kg); // to kg

        // then
        // 10 L -> 10 EA (1 L = 1 EA)
        // 10 EA -> ? KG (1 KG = 0.92 EA, so 1 EA = 1/0.92 KG)
        // 10 EA * (1/0.92) KG/EA = 10 / 0.92 = 10.869565... KG
        BigDecimal expectedValue = new BigDecimal("10").divide(new BigDecimal("0.92"), 5, RoundingMode.HALF_UP);
        assertThat(converted.value()).isEqualByComparingTo(expectedValue);
        assertThat(converted.uom()).isEqualTo(kg);
    }

    @Test
    void convert_변환_비율이_없는_경우_예외가_발생한다() {
        // given
        // baseUom: EA, profile에 L에 대한 정보만 있음
        UomConversionProfile profile = new UomConversionProfile(Set.of(
                new UomConversionFactor(L, BigDecimal.ONE)
        ));
        Item item = createTestItem(EA, profile);
        Quantity quantity = new Quantity(new BigDecimal("10"), L);

        // when & then
        // KG에 대한 비율이 없으므로 예외 발생
        assertThatThrownBy(() -> item.convert(quantity, kg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'" + kg.symbol() + "' 단위에 대한 변환 비율을 찾을 수 없습니다.");
    }

    @Test
    void convert_null_measurement_전달_시_예외가_발생한다() {
        // given
        Item item = createTestItem(EA, new UomConversionProfile(Collections.emptySet()));

        // when & then
        assertThatThrownBy(() -> item.convert(null, kg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("측정 객체는 null일 수 없습니다.");
    }

    @Test
    void convert_null_targetUom_전달_시_예외가_발생한다() {
        // given
        Item item = createTestItem(EA, new UomConversionProfile(Collections.emptySet()));
        Quantity quantity = new Quantity(BigDecimal.TEN, EA);

        // when & then
        assertThatThrownBy(() -> item.convert(quantity, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("목표 단위는 null일 수 없습니다.");
    }
}