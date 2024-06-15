package com.billcom.payment.commons.mappers.bscs;

import com.billcom.payment.commons.domains.bscs.OrderhdrAll;
import com.billcom.payment.commons.dtos.bscs.OrderhdrAllDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderhdrAllMapper {
    OrderhdrAll toEntity(OrderhdrAllDto orderhdrAllDto);

    OrderhdrAllDto toDto(OrderhdrAll orderhdrAll);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderhdrAll partialUpdate(OrderhdrAllDto orderhdrAllDto, @MappingTarget OrderhdrAll orderhdrAll);
}
