package io.lchangha.logisticsexam.masterdata.domain;

import io.lchangha.logisticsexam.masterdata.domain.vo.Barcode;
import io.lchangha.logisticsexam.masterdata.domain.vo.Category;
import io.lchangha.logisticsexam.masterdata.domain.vo.SKU;
import io.lchangha.logisticsexam.masterdata.domain.vo.TemperatureZone;
import io.lchangha.logisticsexam.masterdata.domain.vo.Uom;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Item {
    private Long id;
    private String name;
    private SKU sku;
    private Barcode barcode;
    private Uom baseUom;
    private Category category;
    private TemperatureZone temperatureZone;
    private boolean requiresExpiry;
    private BigDecimal safetyStock;
    private boolean active;
    private LocalDateTime createdAt;
    private String createdBy; //TODO: 유저 만들면 여기에 userId 를 넣기
    private LocalDateTime updatedAt;
    private String updatedBy; //TODO: 유저 만들면 여기에 userId 를 넣기
}
