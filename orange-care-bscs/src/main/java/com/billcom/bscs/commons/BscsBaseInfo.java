package com.billcom.bscs.commons;

import com.billcom.bscs.beans.*;
import org.springframework.http.ResponseEntity;

public interface BscsBaseInfo {

    ResponseEntity<CountriesResponse> getCountries();
    ResponseEntity<RateplansResponse> getRateplans();
    ResponseEntity<TitlesResponse> getTitles();
    ResponseEntity<LanguagesResponse> getLanguages();
    ResponseEntity<MaritalStatusResponse> getMaritalStatuses();
    ResponseEntity<CurrenciesResponse> getCurrencies();
    ResponseEntity<ReasonsResponse> getReasons();
}
