package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Payment;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.PaymentDto;
import uz.pdp.app6contactcompany.repository.PaymentRepository;
import uz.pdp.app6contactcompany.repository.SimCardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    KnowRole knowRole;

    public ApiResponse addPayment(PaymentDto paymentDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(paymentDto.getSimCardId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("sim card not found!", false);

        Payment payment = new Payment();
        payment.setPrice(paymentDto.getPrice());
        payment.setPrice(paymentDto.getPrice());
        payment.setType(paymentDto.getType());
        payment.setFromCardNumber(paymentDto.getFromCardNumber());
        paymentRepository.save(payment);

        SimCard simCard = optionalSimCard.get();
        simCard.setBalance(simCard.getBalance() + paymentDto.getPrice());
        simCardRepository.save(simCard);

        return new ApiResponse("Payment done!", true);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getAllPaymentsBySimCardId(UUID simCardId) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardId);
        if (optionalSimCard.isEmpty())
            return null;
        return paymentRepository.getAllBySimCardId(simCardId);
    }

    public ApiResponse editPayment(UUID id, PaymentDto paymentDto) {

        if (!(knowRole.isDirector()))
            return new ApiResponse("Payment not edited!", false);

        Optional<SimCard> optionalSimCard = simCardRepository.findById(paymentDto.getSimCardId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("sim card not found!", false);

        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isEmpty())
            return new ApiResponse("Payment not found!", false);

        Payment payment = optionalPayment.get();
        payment.setPrice(paymentDto.getPrice());
        payment.setPrice(paymentDto.getPrice());
        payment.setType(paymentDto.getType());
        payment.setFromCardNumber(paymentDto.getFromCardNumber());
        paymentRepository.save(payment);

        return new ApiResponse("Payment edited!", true);

    }

    public ApiResponse deletePayment(UUID id) {
//
        if (!(knowRole.isDirector()))
            return new ApiResponse("Payment not deleted!", false);

        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isEmpty())
            return new ApiResponse("Payment not found!", false);

        paymentRepository.deleteById(id);
        return new ApiResponse("Payment deleted!", true);

    }
}
