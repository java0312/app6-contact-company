package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SimCardDto;
import uz.pdp.app6contactcompany.repository.SimCardRepository;
import uz.pdp.app6contactcompany.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimCardService {

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    KnowRole knowRole;

    @Autowired
    UserRepository userRepository;

    //__ADD
    public ApiResponse addSimCard(SimCardDto simCardDto) {

        if (!(knowRole.isDirector() || knowRole.isNumbersManager()))
            return new ApiResponse("You cannot add sim card", false);

        boolean exists = simCardRepository.existsByCodeAndNumber(simCardDto.getCode(), simCardDto.getNumber());
        if (exists)
            return new ApiResponse("This phone number already taken", false);

        Optional<User> optionalUser = userRepository.findById(simCardDto.getUserId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        SimCard simCard = new SimCard();
        simCard.setPasswordNumber(simCardDto.getPasswordNumber());
        simCard.setUser(optionalUser.get());
        simCard.setCode(simCardDto.getCode());
        simCard.setNumber(simCardDto.getNumber());
        simCard.setBalance(simCardDto.getBalance());
        simCardRepository.save(simCard);
//
        return new ApiResponse("Sim card added!", true);
    }


    //__GET ALL
    public List<SimCard> getAllSimCards() {
        if (!(knowRole.isDirector() || knowRole.isNumbersManager()))
            return null;
        return simCardRepository.findAll();
    }


    //__GET ONE
    public SimCard getSimCard(UUID id) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        if (optionalSimCard.isPresent())
            if (knowRole.isDirector() || knowRole.isNumbersManager() || knowRole.getAuthUser().getId().equals(optionalSimCard.get().getUser().getId()))
                return optionalSimCard.get();
        return null;
    }


    //__EDIT
    public ApiResponse editSimCard(UUID id, SimCardDto simCardDto) {

        if (!(knowRole.isDirector() || knowRole.isNumbersManager()))
            return new ApiResponse("you cannot edit sim card!", false);

        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        if (optionalSimCard.isEmpty())
            return new ApiResponse("SimCard not found!", false);

        boolean exists = simCardRepository.existsByCodeAndNumberAndIdNot(simCardDto.getCode(), simCardDto.getNumber(), id);
        if (exists)
            return new ApiResponse("Sim card already exists!", false);

        Optional<User> optionalUser = userRepository.findById(simCardDto.getUserId());
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        SimCard simCard = optionalSimCard.get();
        simCard.setBalance(simCardDto.getBalance());
        simCard.setUser(optionalUser.get());
        simCard.setNumber(simCardDto.getNumber());
        simCard.setCode(simCardDto.getCode());
        simCard.setPasswordNumber(simCardDto.getPasswordNumber());
        simCardRepository.save(simCard);

        return new ApiResponse("Sim Card edited!", true);
    }


    //__DELETE
    public ApiResponse deleteSimCard(UUID id) {

        if (!(knowRole.isDirector() || knowRole.isNumbersManager()))
            return new ApiResponse("You can't delete sim card", false);

        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        if (optionalSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        simCardRepository.deleteById(id);
        return new ApiResponse("Sim card deleted!", true);
    }
}






