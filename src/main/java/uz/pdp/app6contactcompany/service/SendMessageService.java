package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.SendMessage;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SendMessageDto;
import uz.pdp.app6contactcompany.repository.SendMessageRepository;
import uz.pdp.app6contactcompany.repository.SimCardRepository;
import uz.pdp.app6contactcompany.repository.TariffRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SendMessageService {

    @Autowired
    SendMessageRepository sendMessageRepository;

    @Autowired
    SimCardRepository simCardRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    KnowRole knowRole;


    public ApiResponse addSendMessage(SendMessageDto sendMessageDto) {

        Optional<SimCard> optionalFromSimCard = simCardRepository.findById(sendMessageDto.getFromSimCardId());
        if (optionalFromSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Optional<SimCard> optionalToSimCard = simCardRepository.findById(sendMessageDto.getToSimCardId());
        if (optionalToSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);


        SimCard simCard = optionalFromSimCard.get();
        if (simCard.getSms() > 0)
            simCard.setSms(simCard.getSms() - 1);
        else if (simCard.getBalance() > simCard.getTariff().getSmsPrice())
            simCard.setBalance(simCard.getBalance() - simCard.getTariff().getSmsPrice());
        else
            return new ApiResponse("Not enough money", false);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setFromSimCard(simCard);
        sendMessage.setToSimCard(optionalToSimCard.get());
        sendMessage.setMessage(sendMessageDto.getMessage());

        sendMessageRepository.save(sendMessage);
        return new ApiResponse("Sms sent!", true);
    }

    public List<SendMessage> getAllSendMessage() {
        if (knowRole.isDirector() || knowRole.isNumbersManager())
            return sendMessageRepository.findAll();
        return null;
    }

    public List<SendMessage> getAllSendMessageBySimCardId(UUID simCardId) {

        Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardId);
        if (optionalSimCard.isEmpty())
            return null;

        if (knowRole.isDirector() || knowRole.isNumbersManager()
                || knowRole.getAuthUser().getId().equals(optionalSimCard.get().getUser().getId())) {
            return sendMessageRepository.findAllByFromSimCard_IdOrToSimCard_Id(simCardId, simCardId);
        }

        return null;
    }

    public ApiResponse delete(UUID id) {

        if (!knowRole.isDirector())
            return new ApiResponse("You can't delete", false);

        Optional<SendMessage> optionalSendMessage = sendMessageRepository.findById(id);
        if (optionalSendMessage.isEmpty())
            return new ApiResponse("SendMessage not found!", false);

        sendMessageRepository.deleteById(id);
        return new ApiResponse("SendMassge deleted!", true);
    }

    public ApiResponse editSendMessage(UUID id, SendMessageDto sendMessageDto) {

        Optional<SimCard> optionalFromSimCard = simCardRepository.findById(sendMessageDto.getFromSimCardId());
        if (optionalFromSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Optional<SimCard> optionalToSimCard = simCardRepository.findById(sendMessageDto.getToSimCardId());
        if (optionalToSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Optional<SendMessage> optionalSendMessage = sendMessageRepository.findById(id);
        if (optionalSendMessage.isEmpty())
            return new ApiResponse("SendMessage not found!", false);

        SendMessage sendMessage = optionalSendMessage.get();
        sendMessage.setFromSimCard(optionalFromSimCard.get());
        sendMessage.setToSimCard(optionalToSimCard.get());
        sendMessage.setMessage(sendMessageDto.getMessage());

        sendMessageRepository.save(sendMessage);
        return new ApiResponse("Sms edited!", true);
    }
}
