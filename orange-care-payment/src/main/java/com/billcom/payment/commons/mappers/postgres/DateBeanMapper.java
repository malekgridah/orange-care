package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.DateBean;
import com.billcom.payment.commons.dtos.postgres.DateBeanDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DateBeanMapper {
    DateBean toEntity(DateBeanDto dateBeanDto);

    DateBeanDto toDto(DateBean dateBean);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DateBean partialUpdate(DateBeanDto dateBeanDto, @MappingTarget DateBean dateBean);
}
