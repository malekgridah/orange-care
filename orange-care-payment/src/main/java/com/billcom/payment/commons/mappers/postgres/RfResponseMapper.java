package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.RfResponse;
import com.billcom.payment.commons.dtos.postgres.RfResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RfResponseMapper {
    RfResponse toEntity(RfResponseDto rfResponseDto);

    RfResponseDto toDto(RfResponse rfResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RfResponse partialUpdate(RfResponseDto rfResponseDto, @MappingTarget RfResponse rfResponse);
}
