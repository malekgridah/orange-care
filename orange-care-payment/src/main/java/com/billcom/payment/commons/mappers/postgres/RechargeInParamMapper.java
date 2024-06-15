package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.dtos.postgres.RechargeInParamDto;
import com.billcom.payment.commons.domains.postgres.RechargeInParam;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RechargeInParamMapper {
    RechargeInParam toEntity(RechargeInParamDto rechargeInParamDto);

    RechargeInParamDto toDto(RechargeInParam rechargeInParam);

    List<RechargeInParamDto> toDtoList(List<RechargeInParam> rechargeInParamList);

    List<RechargeInParam> toEntityList(List<RechargeInParamDto> rechargeInParamDtoList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RechargeInParam partialUpdate(RechargeInParamDto rechargeInParamDto, @MappingTarget RechargeInParam rechargeInParam);
}
