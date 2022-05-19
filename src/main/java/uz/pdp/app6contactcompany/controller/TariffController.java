package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SwitchTariffDto;
import uz.pdp.app6contactcompany.service.TariffService;

@RestController
@RequestMapping("/api/tariff")
public class TariffController {


    /**
     * Tarifga o'tish:
     *      tarif ID,
     *      Sim card ID,
     *      balanc yetarli bo'ishi kerak
     *
     */

    @Autowired
    TariffService tariffService;

    @PutMapping("/switchTariff")
    public HttpEntity<?> switchTariff(@RequestBody SwitchTariffDto switchTariffDto){
        ApiResponse apiResponse = tariffService.switchTariff(switchTariffDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
}
