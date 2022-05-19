package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.Call;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.CallDto;
import uz.pdp.app6contactcompany.service.CallService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/call")
public class CallController {


    @Autowired
    CallService callService;

    @PostMapping
    public HttpEntity<?> addCall(@RequestBody CallDto callDto){
        ApiResponse apiResponse = callService.addCall(callDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    /**
     * Director va manager uchun
     * @return
     */
    @GetMapping
    public HttpEntity<?> getAllCall(){
        List<Call> calls = callService.getAllCall();
        return ResponseEntity.ok(calls);
    }

    @GetMapping("/bySimCardId/{simCardId}")
    public HttpEntity<?> getAllCallsBySimCardId(@PathVariable UUID simCardId){
        List<Call> calls = callService.getAllCallsBySimCardId(simCardId);
        return ResponseEntity.ok(calls);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCall(@PathVariable UUID id){
        ApiResponse apiResponse = callService.deleteCall(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
}
