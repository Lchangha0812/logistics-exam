package io.lchangha.logisticsexam.masterdata.domain;

import java.time.LocalDateTime;

public class Location {
    private Long id;
    private String code;
    private String name;
    private LocationType type;
    private Long parentId; // FK to Location.id
    private boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public enum LocationType {
        WH, // Warehouse
        BIN, // Storage Bin
        STAGE, // Staging Area
        REJECT // Reject Area
    }
}
