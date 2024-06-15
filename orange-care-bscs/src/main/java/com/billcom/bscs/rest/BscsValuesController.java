package com.billcom.bscs.rest;

import com.billcom.bscs.commons.BscsBaseInfo;
import com.billcom.bscs.commons.beans.bscs.*;
import com.billcom.bscs.services.BscsValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/bscs")
public class BscsValuesController implements BscsBaseInfo {

    private final BscsValuesService bscsValuesService;

    @Autowired
    public BscsValuesController(BscsValuesService bscsValuesService) {
        this.bscsValuesService = bscsValuesService;
    }

    @Override
    @PostMapping("getCountries")
    public ResponseEntity<CountriesResponse> getCountries() {
        return ResponseEntity.ok(this.bscsValuesService.countriesRead());
    }

    @Override
    @PostMapping("getRateplans")
    public ResponseEntity<RateplansResponse> getRateplans() {
        return ResponseEntity.ok(this.bscsValuesService.rateplansRead());
    }

    @Override
    @PostMapping("getTitles")
    public ResponseEntity<TitlesResponse> getTitles() {
        return ResponseEntity.ok(this.bscsValuesService.titlesRead());
    }

    @Override
    @PostMapping("getLanguages")
    public ResponseEntity<LanguagesResponse> getLanguages() {
        return ResponseEntity.ok(this.bscsValuesService.languagesRead());
    }

    @Override
    @PostMapping("getMaritalStatuses")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatuses() {
        return ResponseEntity.ok(this.bscsValuesService.maritalStatusesRead());
    }

    @Override
    @PostMapping("getCurrencies")
    public ResponseEntity<CurrenciesResponse> getCurrencies() {
        return ResponseEntity.ok(this.bscsValuesService.currenciesRead());
    }

    @Override
    @PostMapping("getReasons")
    public ResponseEntity<ReasonsResponse> getReasons() {
        return ResponseEntity.ok(this.bscsValuesService.reasonsRead());
    }
}
