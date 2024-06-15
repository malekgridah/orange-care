package com.billcom.payment.commons.mappers.bscs;

import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
import com.billcom.payment.commons.domains.bscs.FinTrxInterfaceHist;
import com.billcom.payment.commons.dtos.bscs.FinTrxInterfaceCommonDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FinTrxCommonMapper {
    FinTrxInterface toEntity(FinTrxInterfaceCommonDto finTrxInterfaceDto);

    FinTrxInterfaceCommonDto toDto(FinTrxInterface finTrxInterface);

    FinTrxInterfaceHist toHistEntity(FinTrxInterfaceCommonDto finTrxInterfaceDto);

    FinTrxInterfaceCommonDto toHistDto(FinTrxInterfaceHist finTrxInterface);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FinTrxInterface partialUpdate(FinTrxInterfaceCommonDto finTrxInterfaceCommonDto, @MappingTarget FinTrxInterface finTrxInterface);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FinTrxInterfaceHist partiaHistlUpdate(FinTrxInterfaceCommonDto finTrxInterfaceCommonDto, @MappingTarget FinTrxInterfaceHist finTrxInterfaceHist);
}
