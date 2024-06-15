package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.FailedOperation;
import com.billcom.payment.commons.dtos.postgres.FailedOperationDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PayMapper.class, RechargeInMapper.class, PfResponseMapper.class, RfResponseMapper.class})
public interface FailedOperationMapper {
    FailedOperation toEntity(FailedOperationDto failedOperationDto);

    FailedOperationDto toDto(FailedOperation failedOperation);

    List<FailedOperation> toEntityList(List<FailedOperationDto> failedOperationDtoList);

    List<FailedOperationDto> toDtoList(List<FailedOperation> failedOperationList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FailedOperation partialUpdate(FailedOperationDto failedOperationDto, @MappingTarget FailedOperation failedOperation);

    @AfterMapping
    default void linkPfResponses(@MappingTarget FailedOperation failedOperation) {
        failedOperation.getPfResponses().forEach(PfRespons -> PfRespons.setFailedOperation(failedOperation));
    }

    @AfterMapping
    default void linkRfResponses(@MappingTarget FailedOperation failedOperation) {
        failedOperation.getRfResponses().forEach(RfRespons -> RfRespons.setFailedOperation(failedOperation));
    }
}
