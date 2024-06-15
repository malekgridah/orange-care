package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.PfResponse;
import com.billcom.payment.commons.dtos.postgres.PfResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PfResponseMapper {
    PfResponse toEntity(PfResponseDto pfResponseDto);

    PfResponseDto toDto(PfResponse pfResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PfResponse partialUpdate(PfResponseDto pfResponseDto, @MappingTarget PfResponse pfResponse);
}
