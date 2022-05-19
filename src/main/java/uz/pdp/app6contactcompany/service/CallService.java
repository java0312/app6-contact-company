package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Call;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.CallDto;
import uz.pdp.app6contactcompany.repository.CallRepository;
import uz.pdp.app6contactcompany.repository.SimCardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CallService {

    @Autowired
    CallRepository callRepository;

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    KnowRole knowRole;

    public ApiResponse addCall(CallDto callDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(callDto.getFromSimCardId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Optional<SimCard> optionalSimCard1 = simCardRepository.findById(callDto.getToSimCardId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Call call = new Call();
        call.setFromSimCard(optionalSimCard.get());
        call.setToSimCard(optionalSimCard1.get());
        call.setMinute(callDto.getMinute());

        callRepository.save(call);
        return new ApiResponse("Call added!", true);
    }

    public List<Call> getAllCall() {
        if (knowRole.isDirector() || knowRole.isManager())
            return callRepository.findAll();
        return null;
    }

    public List<Call> getAllCallsBySimCardId(UUID simCardId) {
        if(knowRole.isDirector() || knowRole.isManager())
            return callRepository.findAllByFromSimCard_IdOrToSimCard_Id(simCardId, simCardId);
        return null;
    }

    public ApiResponse deleteCall(UUID id) {
        if (!knowRole.isDirector())
            return new ApiResponse("can't delete", false);

        Optional<Call> optionalCall = callRepository.findById(id);
        if (optionalCall.isPresent()){
            callRepository.deleteById(id);
            return new ApiResponse("Call deleted!", true);
        }

        return new ApiResponse("call not found!", false);
    }
}










