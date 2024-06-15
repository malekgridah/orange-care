package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.Pay;
import com.billcom.payment.commons.dtos.postgres.PayDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PayMapper {
    Pay toEntity(PayDto payDto);

    PayDto toDto(Pay pay);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pay partialUpdate(PayDto payDto, @MappingTarget Pay pay);
}
