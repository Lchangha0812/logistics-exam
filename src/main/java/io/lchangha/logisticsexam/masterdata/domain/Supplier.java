package io.lchangha.logisticsexam.masterdata.domain;

import java.time.LocalDateTime;

public class Supplier {
    private Long id;
    private String name;
    private Integer leadTimeDays;
    private String defaultCurrency;
    private boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
