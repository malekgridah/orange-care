package com.billcom.payment.commons.mappers.postgres;

import com.billcom.payment.commons.domains.postgres.AuthentificationTableKey;
import com.billcom.payment.commons.dtos.postgres.AuthentificationTableKeyDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthentificationTableKeyMapper {
    AuthentificationTableKey toEntity(AuthentificationTableKeyDto authentificationTableKeyDto);

    AuthentificationTableKeyDto toDto(AuthentificationTableKey authentificationTableKey);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuthentificationTableKey partialUpdate(AuthentificationTableKeyDto authentificationTableKeyDto, @MappingTarget AuthentificationTableKey authentificationTableKey);
}
