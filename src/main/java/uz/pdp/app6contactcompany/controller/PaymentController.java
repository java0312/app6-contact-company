package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.Payment;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.PaymentDto;
import uz.pdp.app6contactcompany.service.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public HttpEntity<?> addPayment(@RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = paymentService.addPayment(paymentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /**
     * Barcha tolovlar
     */
    @GetMapping
    public HttpEntity<?> getAllPayments(){
        List<Payment> paymentList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentList);
    }

    /**
     * bitta user tolovlari
     */
    @GetMapping("/bySimCardId/{simCardId}")
    public HttpEntity<?> getAllPaymentsBySimCardId(@PathVariable UUID simCardId){
        List<Payment> payments = paymentService.getAllPaymentsBySimCardId(simCardId);
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editPayment(@PathVariable UUID id, @RequestBody PaymentDto paymentDto){
        ApiResponse apiResponse = paymentService.editPayment(id, paymentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePayment(@PathVariable UUID id){
        ApiResponse apiResponse = paymentService.deletePayment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
