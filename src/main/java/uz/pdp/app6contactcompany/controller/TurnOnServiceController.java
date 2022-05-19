package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.TurnOnService;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.TurnOnServiceDto;
import uz.pdp.app6contactcompany.service.TurnOnServiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/turnOnService")
public class TurnOnServiceController {

    @Autowired
    TurnOnServiceService turnOnServiceService;

    @PostMapping
    public HttpEntity<?> addTurnOnService(@RequestBody TurnOnServiceDto turnOnServiceDto) {
        ApiResponse apiResponse = turnOnServiceService.addTurnOnService(turnOnServiceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllTurnOnService() {
        List<TurnOnService> turnOnServices = turnOnServiceService.getAllTurnOnService();
        return ResponseEntity.ok(turnOnServices);
    }

    @GetMapping("/bySimCardId/{simCardId}")
    public HttpEntity<?> getAllTurnOnServiceBySimCardId(@PathVariable UUID simCardId){
        List<TurnOnService> turnOnServices = turnOnServiceService.getAllTurnOnServiceBySimCardId(simCardId);
        return ResponseEntity.ok(turnOnServices);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTurnOnService(@PathVariable UUID id){
        ApiResponse apiResponse = turnOnServiceService.deleteTurnOnService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
