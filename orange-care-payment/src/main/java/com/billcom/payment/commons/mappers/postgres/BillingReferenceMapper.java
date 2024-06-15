package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.BillingReference;
import com.billcom.payment.commons.dtos.postgres.BillingReferenceDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BillingReferenceMapper {
    BillingReference toEntity(BillingReferenceDto billingReferenceDto);

    BillingReferenceDto toDto(BillingReference billingReference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BillingReference partialUpdate(BillingReferenceDto billingReferenceDto, @MappingTarget BillingReference billingReference);
}
