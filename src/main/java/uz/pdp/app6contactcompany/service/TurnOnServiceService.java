package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.entity.TurnOnService;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.TurnOnServiceDto;
import uz.pdp.app6contactcompany.repository.ServiceRepository;
import uz.pdp.app6contactcompany.repository.SimCardRepository;
import uz.pdp.app6contactcompany.repository.TurnOnServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TurnOnServiceService {

    @Autowired
    TurnOnServiceRepository turnOnServiceRepository;

    @Autowired
    KnowRole knowRole;

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ApiResponse addTurnOnService(TurnOnServiceDto turnOnServiceDto) {

        Optional<SimCard> optionalSimCard = simCardRepository.findById(turnOnServiceDto.getSimCardId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("Sim Card not found!", false);

        Optional<uz.pdp.app6contactcompany.entity.Service> optionalService = serviceRepository.findById(turnOnServiceDto.getServiceId());
        if (optionalService.isEmpty())
            return new ApiResponse("Service not found!", false);


        SimCard simCard = optionalSimCard.get();
        if (simCard.getBalance() < optionalService.get().getPrice())
            return new ApiResponse("Not enough money", false);



        double balance = simCard.getBalance() - optionalService.get().getPrice();
        if (balance < 0)
            return new ApiResponse("Not enough money", false);


        simCard.setBalance(balance);
        simCardRepository.save(simCard);

        if (!knowRole.getAuthUser().getId().equals(optionalSimCard.get().getUser().getId()))
            return new ApiResponse("You can't add service to this sim card", false);

        boolean exists = turnOnServiceRepository.existsBySimCardIdAndServiceId(turnOnServiceDto.getSimCardId(), turnOnServiceDto.getServiceId());
        if (exists)
            return new ApiResponse("Service turned on!", false);

        TurnOnService turnOnService = new TurnOnService();
        turnOnService.setService(optionalService.get());
        turnOnService.setSimCard(optionalSimCard.get());

        TurnOnService service = turnOnServiceRepository.save(turnOnService);


        return new ApiResponse("TurnOnService added!", true);
    }

    /**
     * Direktor uchun
     *
     * @return
     */
    public List<TurnOnService> getAllTurnOnService() {
        if (knowRole.isDirector())
            return turnOnServiceRepository.findAll();
        return null;
    }


    /**
     * User uchun
     *
     * @param simCardId
     * @return
     */
    public List<TurnOnService> getAllTurnOnServiceBySimCardId(UUID simCardId) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardId);
        if (optionalSimCard.isEmpty())
            return null;

        if (!knowRole.getAuthUser().getId().equals(optionalSimCard.get().getUser().getId()))
            return null;

        return turnOnServiceRepository.findAllBySimCardId(simCardId);
    }

    public ApiResponse deleteTurnOnService(UUID id) {

        Optional<TurnOnService> optionalTurnOnService = turnOnServiceRepository.findById(id);
        if(optionalTurnOnService.isEmpty())
            return new ApiResponse("Turn on service not found!", false);

        if (!optionalTurnOnService.get().getSimCard().getUser().getId().equals(knowRole.getAuthUser().getId()))
            return new ApiResponse("You can't delete!", false);

        turnOnServiceRepository.deleteById(id);
        return new ApiResponse("Turn on service deleted!", false);
    }
}
