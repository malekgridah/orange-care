package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.RechargeIn;
import com.billcom.payment.commons.dtos.postgres.RechargeInDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RechargeInMapper {
    RechargeIn toEntity(RechargeInDto rechargeInDto);

    RechargeInDto toDto(RechargeIn rechargeIn);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RechargeIn partialUpdate(RechargeInDto rechargeInDto, @MappingTarget RechargeIn rechargeIn);
}
