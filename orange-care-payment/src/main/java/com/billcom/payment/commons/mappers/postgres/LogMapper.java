package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.Log;
import com.billcom.payment.commons.dtos.postgres.LogDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LogMapper {
    Log toEntity(LogDto logDto);

    LogDto toDto(Log log);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Log partialUpdate(LogDto logDto, @MappingTarget Log log);
}
