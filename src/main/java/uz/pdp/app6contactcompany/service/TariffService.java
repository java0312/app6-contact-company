package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.SimCard;
import uz.pdp.app6contactcompany.entity.Tariff;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SwitchTariffDto;
import uz.pdp.app6contactcompany.repository.SimCardRepository;
import uz.pdp.app6contactcompany.repository.TariffRepository;
import uz.pdp.app6contactcompany.repository.UserRepository;

import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    SimCardRepository simCardRepository;

    public ApiResponse switchTariff(SwitchTariffDto switchTariffDto) {

        Optional<SimCard> optionalSimCard = simCardRepository.findById(switchTariffDto.getTariffId());
        if (optionalSimCard.isEmpty())
            return new ApiResponse("Sim card not found!", false);

        Optional<Tariff> optionalTariff = tariffRepository.findById(switchTariffDto.getTariffId());
        if (optionalTariff.isEmpty())
            return new ApiResponse("Tariff not found!", false);

        Tariff tariff = optionalTariff.get();
        SimCard simCard = optionalSimCard.get();

        double money = simCard.getBalance() - tariff.getPrice() - tariff.getPassPrice();
        if (money < 0)
            return new ApiResponse("Not enough money", false);

        simCard.setTariff(tariff);
        simCard.setBalance(money);
        simCard.setMin(tariff.getMinuteInNet());
        simCard.setSms(tariff.getSms());
        simCard.setMg(tariff.getMegabyteIn());
        simCardRepository.save(simCard);

        return new ApiResponse("Tariff changed", true);
    }
}
