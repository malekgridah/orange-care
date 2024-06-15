package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.CustomerReference;
import com.billcom.payment.commons.dtos.postgres.CustomerReferenceDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerReferenceMapper {
    CustomerReference toEntity(CustomerReferenceDto customerReferenceDto);

    CustomerReferenceDto toDto(CustomerReference customerReference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerReference partialUpdate(CustomerReferenceDto customerReferenceDto, @MappingTarget CustomerReference customerReference);
}
