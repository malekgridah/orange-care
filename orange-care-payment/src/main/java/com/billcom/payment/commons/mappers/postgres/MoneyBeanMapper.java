package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.MoneyBean;
import com.billcom.payment.commons.dtos.postgres.MoneyBeanDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MoneyBeanMapper {
    MoneyBean toEntity(MoneyBeanDto moneyBeanDto);

    MoneyBeanDto toDto(MoneyBean moneyBean);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MoneyBean partialUpdate(MoneyBeanDto moneyBeanDto, @MappingTarget MoneyBean moneyBean);
}
