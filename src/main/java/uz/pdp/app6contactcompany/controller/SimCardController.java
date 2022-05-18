package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SimCardDto;
import uz.pdp.app6contactcompany.service.SimCardService;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/cimCard")
public class SimCardController {

    @Autowired
    SimCardService simCardService;

    /*
    * Biror userga simkarta biriktirish,
    * Simcartani Director yoki raqamlar bilan ishlovchi manager qoshadi
    * */
    @PostMapping
    public HttpEntity<?> addSimCard(@RequestBody SimCardDto simCardDto){
        ApiResponse apiResponse = simCardService.addSimCard(simCardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /*
    * Xodimdan kattalari uchun
    * */
    @GetMapping
    public HttpEntity<?> getAllSimCard(){
        List<SimCard> allSimCards = simCardService.getAllSimCards();
        return ResponseEntity.ok(allSimCards);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getSimCard(@PathVariable UUID id){
        SimCard simCard = simCardService.getSimCard(id);
        return ResponseEntity.ok(simCard);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSimCard(@PathVariable UUID id, @RequestBody SimCardDto simCardDto){
        ApiResponse apiResponse = simCardService.editSimCard(id, simCardDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSimCard(@PathVariable UUID id){
        ApiResponse apiResponse = simCardService.deleteSimCard(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
