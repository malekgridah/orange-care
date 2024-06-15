package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.AuthentificationTable;
import com.billcom.payment.commons.dtos.postgres.AuthentificationTableDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthentificationTableKeyMapper.class})
public interface AuthentificationTableMapper {
    AuthentificationTable toEntity(AuthentificationTableDto authentificationTableDto);

    AuthentificationTableDto toDto(AuthentificationTable authentificationTable);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuthentificationTable partialUpdate(AuthentificationTableDto authentificationTableDto, @MappingTarget AuthentificationTable authentificationTable);
}
