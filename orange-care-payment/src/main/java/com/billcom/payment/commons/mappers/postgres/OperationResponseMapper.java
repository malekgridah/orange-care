package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.dtos.postgres.OperationResponseDto;
import com.billcom.payment.commons.domains.postgres.OperationResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationResponseMapper {
    OperationResponse toEntity(OperationResponseDto operationResponseDto);

    OperationResponseDto toDto(OperationResponse operationResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OperationResponse partialUpdate(OperationResponseDto operationResponseDto, @MappingTarget OperationResponse operationResponse);
}
