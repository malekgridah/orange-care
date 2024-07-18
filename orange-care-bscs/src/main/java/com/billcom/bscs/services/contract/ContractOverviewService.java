package com.billcom.bscs.services.contract;

import com.billcom.bscs.clients.wsi.*;
import com.billcom.bscs.commons.beans.contract.overview.*;
import com.ericsson.contractdevicesread.ContractDevicesReadRequest;
import com.ericsson.contractread.ContractReadRequest;
import com.ericsson.contractread.ContractReadResponse;
import com.ericsson.contractread.InputAttributes;
import com.ericsson.contractresourcesread.ContractResourcesReadRequest;
import com.ericsson.contractresourcesread.ContractResourcesReadResponse;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadRequest;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadResponse;
import com.ericsson.contractservice.parametersread.NumParamsResponse;
import com.ericsson.contractservicesread.ContractServicesReadRequest;
import com.ericsson.contractservicesread.ContractServicesReadResponse;
import com.ericsson.contractservicesread.ServicesListpartResponse;
import com.ericsson.servicepackagesread.NumSpListpartResponse;
import com.ericsson.servicepackagesread.ServicePackagesReadRequest;
import com.ericsson.servicesread.ServicesReadRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ContractOverviewService {

    private final ContractReadClient contractReadClient;
    private final ContractServicesReadClient contractServicesReadClient;
    private final ServicePackagesReadClient servicePackagesReadClient;
    private final ServicesReadClient servicesReadClient;
    private final ContractServiceParametersReadClient serviceParametersReadClient;
    private final ContractDevicesReadClient contractDevicesReadClient;
    private final ContractResourcesReadClient contractResourcesReadClient;

    @Autowired
    public ContractOverviewService(ContractReadClient contractReadClient,
                                   ContractServicesReadClient contractServicesReadClient,
                                   ServicePackagesReadClient servicePackagesReadClient,
                                   ServicesReadClient servicesReadClient,
                                   ContractServiceParametersReadClient serviceParametersReadClient,
                                   ContractDevicesReadClient contractDevicesReadClient,
                                   ContractResourcesReadClient contractResourcesReadClient) {
        this.contractReadClient = contractReadClient;
        this.contractServicesReadClient = contractServicesReadClient;
        this.servicePackagesReadClient = servicePackagesReadClient;
        this.servicesReadClient = servicesReadClient;
        this.serviceParametersReadClient = serviceParametersReadClient;
        this.contractDevicesReadClient = contractDevicesReadClient;
        this.contractResourcesReadClient = contractResourcesReadClient;
    }

    public ContractOverviewResponse contractOverview(ContractOverViewRequest overViewRequest) {
        ContractOverviewResponse overviewResponse = new ContractOverviewResponse();
        overviewResponse.setCoId(overviewResponse.getCoId());
        overviewResponse.setContract(this.contractRead(overViewRequest));
        return overviewResponse;
    }

    private Contract contractRead(ContractOverViewRequest overViewRequest) {
        Contract contract = new Contract();
        ContractReadResponse readResponse;
        ContractReadRequest contractReadRequest = new ContractReadRequest();
        InputAttributes inputAttributes = new InputAttributes();

        if (overViewRequest.getCoId() != null) {
            inputAttributes.setCoId(overViewRequest.getCoId());
        }

        if (overViewRequest.getCoCode() != null && !overViewRequest.getCoCode().isBlank()) {
            inputAttributes.setCoIdPub(overViewRequest.getCoCode());
        }
        contractReadRequest.setInputAttributes(inputAttributes);

        readResponse = this.contractReadClient.execute(contractReadRequest,"ADMX","ADMX");

        if (readResponse != null) {
            contract.setCoId(readResponse.getCoId());
            contract.setCoCode(readResponse.getCoIdPub());
            contract.setCoStatus(Integer.valueOf(readResponse.getCoStatus().toString()));
            contract.setCsId(readResponse.getCsId());
            contract.setCsIdPub(readResponse.getCsIdPub());

            contract.setCoLastReason(readResponse.getCoLastReason());
            contract.setReason(readResponse.getReason());
            contract.setReasonShdes(readResponse.getReasonShdes());

            contract.setScCode(readResponse.getSccode());
            contract.setScCodePub(readResponse.getSccodePub());
            contract.setSubMarket(readResponse.getSubmId());
            contract.setSubMarketIdPub(readResponse.getSubmIdPub());

            contract.setScCodePub(readResponse.getSccodePub());
            contract.setScCodePub(readResponse.getSccodePub());
            contract.setResources(this.contractResourcesRead(readResponse.getCoId(), readResponse.getRpcode()));

            if (readResponse.getCoActivated() != null) {
                contract.setCoActivatedDate(readResponse.getCoActivated().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            }

            if (readResponse.getCoEntdate() != null) {
                contract.setCoEntDate(readResponse.getCoEntdate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            }

            if (readResponse.getCoModdate() != null) {
                contract.setCoModDate(readResponse.getCoModdate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            }

            if (readResponse.getCoSignedDate() != null) {
                contract.setCoSignedDate(readResponse.getCoSignedDate().toGregorianCalendar().toZonedDateTime().toLocalDate());
            }

            if (readResponse.getCoPendingDate() != null) {
                contract.setCoPendingDate(readResponse.getCoPendingDate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            }

            if (readResponse.getCoLastStatusChangeDate() != null) {
                contract.setCoLastStatusChangeDate(readResponse.getCoLastStatusChangeDate().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            }

            contract.setContractServiceNode(this.contractServicesRead(readResponse.getCoId(), readResponse.getRpcode()));
        }
        return contract;
    }

    private String findEquivalentName(String spCode, List<NumSpListpartResponse> spCodesList) {
        return Objects.requireNonNull(spCodesList.stream()
                .filter(sp -> spCode.trim().equalsIgnoreCase(sp.getSpcodePub().trim()))
                .findFirst()
                .orElse(null)).getSpDes();
    }

    private ContractServiceNode[] contractServicesRead(Long coId, Long rateplan) {
        List<ContractServiceNode> contractServiceNodeList = new ArrayList<>();

        ContractServicesReadRequest servicesReadRequest = new ContractServicesReadRequest();
        ContractServicesReadResponse servicesReadResponse;
        com.ericsson.contractservicesread.InputAttributes inputAttributes = new com.ericsson.contractservicesread.InputAttributes();
        inputAttributes.setCoId(coId);
        servicesReadRequest.setInputAttributes(inputAttributes);

        servicesReadResponse = this.contractServicesReadClient.execute(servicesReadRequest, "ADMX","ADMX");
        List<NumSpListpartResponse> spCodesList = this.servicePackagesRead(rateplan);
        if (servicesReadResponse != null) {
            Map<String, List<ServicesListpartResponse>> spcodeToServicesMap = servicesReadResponse.getServices()
                    .getItem()
                    .stream()
                    .collect(Collectors.groupingBy(ServicesListpartResponse::getSpcodePub));

            spcodeToServicesMap.forEach((servicePackage, services) -> {
                ContractServiceNode serviceNode = new ContractServiceNode();
                serviceNode.setService(this.findEquivalentName(servicePackage, spCodesList));
                List<ContractServiceNode> serviceNodes = new ArrayList<>();
                services.forEach(service -> {
                    ContractServiceNode contractServiceNode1 = new ContractServiceNode();
                    contractServiceNode1.setService(this.servicesRead(service.getSncodePub()));
                    contractServiceNode1.setOneTimeCharge(service.getCalcSub());
                    contractServiceNode1.setStatus(String.valueOf(service.getCosStatus()));
                    if (service.getCosStatusDate() != null) {
                        contractServiceNode1.setValidFrom(service
                                .getCosStatusDate()
                                .toGregorianCalendar()
                                .toZonedDateTime()
                                .toLocalDateTime());
                    }
                    if (service.getCosPendingStatus() != null) {
                        contractServiceNode1.setPendingStatus(String.valueOf(service.getCosPendingStatus()));
                    }
                    if (service.getCsPaymentConditionUsgInd() != null) {
                        contractServiceNode1.setPaymentOption(String.valueOf(service.getCsPaymentConditionUsgInd()));
                    }
                    contractServiceNode1.setRecurringCharge(service.getOrigAcc());
                    if (service.getDirectoryNumbers() != null) {
                        service.getDirectoryNumbers().getItem().forEach(item -> contractServiceNode1
                                .setResource(item.getDirnum()));
                    }
                    if (service.isParamsInd() != null && service.isParamsInd()) {
                        List<ContractServiceNode> contractServiceNodes = new ArrayList<>();
                        NumParamsResponse numParamsResponse= this.serviceParametersReadResponse(coId,
                                service.getSpcode(), service.getProfileId(), service.getSncode());
                        numParamsResponse.getItem().forEach(numParamsItem -> {
                            ContractServiceNode contractServiceNode2 = new ContractServiceNode();
                            contractServiceNode2.setService(numParamsItem.getPrmDes());
                            numParamsItem.getMultValues()
                                    .getItem()
                                    .forEach(item -> {
                                        if (item.getValueDes() != null) {
                                            contractServiceNode2.setValue(item.getValueDes());
                                        } else {
                                            contractServiceNode2.setValue(item.getValue());
                                        }
                                        log.info(item.getValueDes());
                                    });
                            contractServiceNodes.add(contractServiceNode2);
                        });
                        ContractServiceNode[] serviceNodeArrays = new ContractServiceNode[contractServiceNodes.size()];
                        contractServiceNodes.toArray(serviceNodeArrays);
                        contractServiceNode1.setChildren(serviceNodeArrays);
                    }
                    serviceNodes.add(contractServiceNode1);
                });
                ContractServiceNode[] serviceNodeArray = new ContractServiceNode[serviceNodes.size()];
                serviceNodes.toArray(serviceNodeArray);
                serviceNode.setChildren(serviceNodeArray);

                contractServiceNodeList.add(serviceNode);

            });
        }
        return contractServiceNodeList.toArray(new ContractServiceNode[0]);
    }

    private NumParamsResponse serviceParametersReadResponse(Long coId, Long spCode, Long profileId, Long snCode) {
        ContractServiceParametersReadResponse contractServiceParametersReadResponse;
        ContractServiceParametersReadRequest parametersReadRequest = new ContractServiceParametersReadRequest();
        com.ericsson.contractservice.parametersread.InputAttributes inputAttributes = new com.ericsson.contractservice.parametersread.InputAttributes();

        inputAttributes.setCoId(coId);
        inputAttributes.setProfileId(profileId);
        inputAttributes.setSpcode(spCode);
        inputAttributes.setSncode(snCode);
        parametersReadRequest.setInputAttributes(inputAttributes);

        contractServiceParametersReadResponse = this.serviceParametersReadClient
                .execute(parametersReadRequest, "ADMX","ADMX");

        return contractServiceParametersReadResponse.getNumParams();
    }

    private String servicesRead(String snShdes) {
        AtomicReference<String> serviceName = new AtomicReference<>("");
        ServicesReadRequest readRequest = new ServicesReadRequest();

        com.ericsson.servicesread.InputAttributes inputAttributes = new com.ericsson.servicesread.InputAttributes();
        inputAttributes.setSncodePub(snShdes);
        readRequest.setInputAttributes(inputAttributes);

        this.servicesReadClient.execute(readRequest, "ADMX","ADMX")
                .getNumSv()
                .getItem()
                .forEach(num -> serviceName.set(num.getSvDes()));
        return serviceName.get();
    }

    private List<NumSpListpartResponse> servicePackagesRead(Long rpCode) {
        ServicePackagesReadRequest packagesReadRequest = new ServicePackagesReadRequest();
        com.ericsson.servicepackagesread.InputAttributes inputAttributes = new com.ericsson.servicepackagesread.InputAttributes();

        inputAttributes.setRpcode(rpCode);
        packagesReadRequest.setInputAttributes(inputAttributes);
        return this.servicePackagesReadClient.execute(packagesReadRequest, "ADMX","ADMX")
                .getNumSp()
                .getItem();
    }

    private ContractDevices contractDevicesRead(Long coId) {
        ContractDevicesReadRequest contractDevicesReadRequest = new ContractDevicesReadRequest();
        com.ericsson.contractdevicesread.InputAttributes inputAttributes = new com.ericsson.contractdevicesread.InputAttributes();

        inputAttributes.setCoId(coId);
        return null;
    }

    private ServicesListpartResponse contractServiceRead(Long coId, Long profileId, String snCodePub) {
        ContractServicesReadRequest request = new ContractServicesReadRequest();
        com.ericsson.contractservicesread.InputAttributes inputAttributes = new com.ericsson.contractservicesread.InputAttributes();

        inputAttributes.setCoId(coId);
        inputAttributes.setProfileId(profileId);
        inputAttributes.setSncodePub(snCodePub);
        request.setInputAttributes(inputAttributes);

        ContractServicesReadResponse response = this.contractServicesReadClient.execute(request, "ADMX", "ADMX");
        if (response != null && response.getServices().getItem() != null) return response.getServices().getItem().get(0);

        return null;
    }

    private String getBaseSpCodeDes(Long rateplan, Long spCode) {
        ServicePackagesReadRequest packagesReadRequest = new ServicePackagesReadRequest();
        com.ericsson.servicepackagesread.InputAttributes inputAttributes = new com.ericsson.servicepackagesread.InputAttributes();

        inputAttributes.setRpcode(rateplan);
        packagesReadRequest.setInputAttributes(inputAttributes);
        return this.servicePackagesReadClient.execute(packagesReadRequest, "ADMX","ADMX")
                .getNumSp()
                .getItem()
                .stream()
                .filter(num -> num.getSpcode().equals(spCode))
                .toList()
                .get(0)
                .getSpDes();
    }

    private ContractResources contractResourcesRead(Long coId, Long rateplan) {
        ContractResources resources = new ContractResources();
        ContractResourcesReadRequest request = new ContractResourcesReadRequest();
        com.ericsson.contractresourcesread.InputAttributes inputAttributes = new com.ericsson.contractresourcesread.InputAttributes();

        inputAttributes.setCoId(coId);
        request.setInputAttributes(inputAttributes);

        ContractResourcesReadResponse response = this.contractResourcesReadClient.execute(request, "ADMX", "ADMX");
        if (response != null) {
            if (response.getPortNum() != null) resources.setPortNum(response.getPortNum());
            if (response.getSmSerialnum() != null) resources.setSmSerialNum(response.getSmSerialnum());

            List<ContractDirectoryNumbers> dirNums = new ArrayList<>();

            if (response.getListOfServices() != null && response.getListOfServices().getItem() != null) {
                response.getListOfServices().getItem().forEach(service -> {
                    ContractDirectoryNumbers dirNum = new ContractDirectoryNumbers();
                    if (service.getSncode() != null) dirNum.setSnCode(service.getSncode());
                    if (service.getSncodePub() != null) dirNum.setSnCodePub(service.getSncodePub());
                    if (service.getProfileId() != null) dirNum.setProfileId(service.getProfileId());

                    if (service.getListOfDirectoryNumbers() != null && service.getListOfDirectoryNumbers().getItem() != null) {
                        service.getListOfDirectoryNumbers().getItem().forEach(dir -> {
                            if (dir.getDirnum() != null) dirNum.setDirNum(dir.getDirnum());
                        });
                    }
                    ServicesListpartResponse contractService = this.contractServiceRead(coId, service.getProfileId(), service.getSncodePub());
                    if (contractService != null) {
                        dirNum.setSnCodeDes(this.servicesRead(service.getSncodePub()));
                        contractService.getDirectoryNumbers().getItem().forEach(coService -> {
                            if (coService.getCoscDnStatus() != null) dirNum.setDnStatus(coService.getCoscDnStatus());
                            if (coService.isMainDirnum() != null) dirNum.setMainDirNum(coService.isMainDirnum());
                            if (coService.isDirnumOnBill() != null) dirNum.setDirNumOnBill(coService.isDirnumOnBill());
                        });
                        dirNum.setSpCodeDes(this.getBaseSpCodeDes(rateplan,contractService.getSpcode()));
                        dirNum.setSpCodePub(contractService.getSpcodePub());
                        dirNum.setSpCode(contractService.getSpcode());
                        dirNum.setSpCodeDes(this.getBaseSpCodeDes(rateplan,contractService.getSpcode()));
                    }
                    dirNums.add(dirNum);
                });
                resources.setDirNums(dirNums);
            }
        }
        return resources;
    }
}
