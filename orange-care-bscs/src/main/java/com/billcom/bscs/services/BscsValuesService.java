package com.billcom.bscs.services;

import com.billcom.bscs.beans.*;
import com.billcom.bscs.clients.wsi.*;
import com.ericsson.countriesread.CountriesReadRequest;
import com.ericsson.currenciesread.CurrenciesReadRequest;
import com.ericsson.languagesread.LanguagesReadRequest;
import com.ericsson.martitalstatusread.MaritalStatusReadRequest;
import com.ericsson.rateplansread.InputAttributes;
import com.ericsson.rateplansread.RateplansReadRequest;
import com.ericsson.reasonsread.ReasonsReadRequest;
import com.ericsson.titlesread.TitlesReadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BscsValuesService {

    private final TitlesReadClient titlesReadClient;
    private final ReasonsReadClient reasonsReadClient;
    private final RateplansReadClient rateplansReadClient;
    private final LanguagesReadClient languagesReadClient;
    private final CountriesReadClient countriesReadClient;
    private final CurrenciesReadClient currenciesReadClient;
    private final MaritalStatusReadClient maritalStatusReadClient;

    @Autowired
    public BscsValuesService(TitlesReadClient titlesReadClient,
                             ReasonsReadClient reasonsReadClient,
                             RateplansReadClient rateplansReadClient,
                             LanguagesReadClient languagesReadClient,
                             CountriesReadClient countriesReadClient,
                             CurrenciesReadClient currenciesReadClient,
                             MaritalStatusReadClient maritalStatusReadClient) {
        this.titlesReadClient = titlesReadClient;
        this.reasonsReadClient = reasonsReadClient;
        this.rateplansReadClient = rateplansReadClient;
        this.languagesReadClient = languagesReadClient;
        this.countriesReadClient = countriesReadClient;
        this.currenciesReadClient = currenciesReadClient;
        this.maritalStatusReadClient = maritalStatusReadClient;
    }

    public CountriesResponse countriesRead() {
        CountriesResponse countriesResponse = new CountriesResponse();
        List<Country> countries = new ArrayList<>();

        this.countriesReadClient.execute(new CountriesReadRequest(),null,null)
                .getCountries()
                .getItem()
                .forEach(countriesListpartResponse -> {
                    Country country = new Country();
                    country.setCountryName(countriesListpartResponse.getCntrDes());
                    country.setCountryId(countriesListpartResponse.getCountryId());
                    country.setCountryIdPub(countriesListpartResponse.getCountryIdPub());
                    countries.add(country);
                });

        countriesResponse.setCountries(countries);
        return countriesResponse;
    }

    public RateplansResponse rateplansRead() {
        RateplansResponse rateplansResponse = new RateplansResponse();
        List<Rateplan> rateplans = new ArrayList<>();

        RateplansReadRequest request = new RateplansReadRequest();
        InputAttributes inputAttributes = new InputAttributes();
        request.setInputAttributes(inputAttributes);

        this.rateplansReadClient.execute(request,null,null)
                .getNumRp()
                .getItem()
                .forEach(response -> {
                    Rateplan rateplan = new Rateplan();
                    rateplan.setRpCode(response.getRpcode());
                    rateplan.setRpDes(response.getRpDes());
                    rateplan.setRpOcc(response.isRpOcc() != null && response.isRpOcc());
                    rateplan.setRpCodePub(response.getRpcodePub());
                    rateplan.setScope(response.getScope());
                    rateplans.add(rateplan);
                });

        rateplansResponse.setRateplans(rateplans);
        return rateplansResponse;
    }

    public TitlesResponse titlesRead() {
        TitlesResponse titlesResponse = new TitlesResponse();
        List<Title> titles = new ArrayList<>();

        this.titlesReadClient.execute(new TitlesReadRequest(),null,null)
                .getListOfTitles()
                .getItem()
                .forEach(response -> {
                    Title title = new Title();
                    title.setTtlId(response.getTtlId());
                    title.setTtlIdPub(response.getTtlIdPub());
                    title.setTtlDes(response.getTtlDes());
                    title.setTtlGender(response.getTtlGender());
                    titles.add(title);
                });

        titlesResponse.setTitles(titles);
        return titlesResponse;
    }

    public MaritalStatusResponse maritalStatusesRead() {
        MaritalStatusResponse maritalStatusResponse = new MaritalStatusResponse();
        List<MaritalStatus> maritalStatuses = new ArrayList<>();

        this.maritalStatusReadClient.execute(new MaritalStatusReadRequest(),null,null)
                .getListOfMaritalStatus()
                .getItem()
                .forEach(response -> {
                    MaritalStatus maritalStatus = new MaritalStatus();
                    maritalStatus.setMasCode(response.getMasCode());
                    maritalStatus.setMasDes(response.getMasDes());
                    maritalStatus.setMasCodePub(response.getMasCodePub());
                    maritalStatuses.add(maritalStatus);
                });

        maritalStatusResponse.setMaritalStatuses(maritalStatuses);
        return maritalStatusResponse;
    }

    public LanguagesResponse languagesRead() {
        LanguagesResponse languagesResponse = new LanguagesResponse();
        List<Language> languages = new ArrayList<>();

        this.languagesReadClient.execute(new LanguagesReadRequest(),null,null)
                .getListOfLanguages()
                .getItem()
                .forEach(response -> {
                    Language language = new Language();
                    language.setLngCode(response.getLngCode());
                    language.setLngCodePub(response.getLngCodePub());
                    language.setLngDes(response.getLngDes());
                    languages.add(language);
                });

        languagesResponse.setLanguages(languages);
        return languagesResponse;
    }

    public CurrenciesResponse currenciesRead() {
        CurrenciesResponse currenciesResponse = new CurrenciesResponse();
        List<Currency> currencies = new ArrayList<>();

        this.currenciesReadClient.execute(new CurrenciesReadRequest(),null,null)
                .getCurrencies()
                .getItem()
                .forEach(response -> {
                    Currency currency = new Currency();
                    currency.setCurrencyId(response.getCurrencyId());
                    currency.setCurrencyIdPub(response.getCurrencyIdPub());
                    currency.setFcCode(response.getFcCode());
                    currency.setFcDesc(response.getFcDesc());
                    currencies.add(currency);
                });

        currenciesResponse.setCurrencies(currencies);
        return currenciesResponse;
    }

    public ReasonsResponse reasonsRead() {
        ReasonsResponse reasonsResponse = new ReasonsResponse();
        List<Reason> reasons = new ArrayList<>();

        this.reasonsReadClient.execute(new ReasonsReadRequest(),null,null)
                .getReasons()
                .getItem()
                .forEach(response -> {
                    Reason reason = new Reason();
                    reason.setRsCode(response.getRsCode());
                    reason.setRsDes(response.getRsDes());
                    reason.setRsState(response.getRsState());
                    reason.setScope(response.getScope());
                    reasons.add(reason);
                });

        reasonsResponse.setReasons(reasons);
        return reasonsResponse;
    }
}
