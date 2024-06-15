package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.GetInvoice;
import com.billcom.payment.commons.dtos.postgres.GetInvoiceDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GetInvoiceMapper {
    GetInvoice toEntity(GetInvoiceDto getInvoiceDto);

    GetInvoiceDto toDto(GetInvoice getInvoice);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GetInvoice partialUpdate(GetInvoiceDto getInvoiceDto, @MappingTarget GetInvoice getInvoice);
}
