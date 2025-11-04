package io.lchangha.logisticsexam.application.inbound.inboundorder.mapper;

import io.lchangha.logisticsexam.application.inbound.inboundorder.command.RegisterInboundOrderCommand;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.*;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.params.RegistrationInfo;
import io.lchangha.logisticsexam.domain.masterdata.partner.UnitOfMeasure;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import org.mapstruct.Mapper;


@Mapper
public abstract class InboundOrderMapper {

    public abstract RegistrationInfo toRegistrationInfo(RegisterInboundOrderCommand command);

    public abstract RegistrationInfo.RegisterItem toRegisterItem(RegisterInboundOrderCommand.RegisterItem item);

    protected ReferenceCode toReferenceCode(RegisterInboundOrderCommand command) {
        if (command == null || command.referenceType() == null || command.referenceValue() == null) {
            return null;
        }

        InboundOrderType type = InboundOrderType.valueOf(command.referenceType());
        String value = command.referenceValue();

        return switch (type) {
            case STANDARD_PURCHASE -> new PurchaseOrderCode(value);
            case RETURN -> new RmaCode(value);
            case WAREHOUSE_TRANSFER -> new TransferOrderCode(value);
        };
    }

    protected Quantity toQuantity(Long amount, String unit) {
        if (amount == null || unit == null) {
            return null;
        }
        return new Quantity(amount, UnitOfMeasure.valueOf(unit));
    }

    protected InboundOrderTitle toTitle(String title) {
        return new InboundOrderTitle(title);
    }

    protected InboundOrderDescription toDescription(String description) {
        return new InboundOrderDescription(description);
    }

    protected ProductId toProductId(Long id) {
        return new ProductId(id);
    }
}
