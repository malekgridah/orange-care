package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.dtos.postgres.SmsConfigDto;
import com.billcom.payment.commons.domains.postgres.SmsConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SmsConfigMapper {
    SmsConfig toEntity(SmsConfigDto smsConfigDto);

    SmsConfigDto toDto(SmsConfig smsConfig);

    List<SmsConfig> toListEntity(List<SmsConfigDto> smsConfigDtoList);

    List<SmsConfigDto> toDtoList(List<SmsConfig> smsConfigList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SmsConfig partialUpdate(SmsConfigDto smsConfigDto, @MappingTarget SmsConfig smsConfig);
}
