package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.app6contactcompany.entity.Category;
import uz.pdp.app6contactcompany.entity.Service;
import uz.pdp.app6contactcompany.entity.UssdCode;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.ServiceDto;
import uz.pdp.app6contactcompany.repository.CategoryRepository;
import uz.pdp.app6contactcompany.repository.ServiceRepository;
import uz.pdp.app6contactcompany.repository.UssdCodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UssdCodeRepository ussdCodeRepository;

    @Autowired
    KnowRole knowRole;

    //__ADD
    public ApiResponse addService(ServiceDto serviceDto) {
        if (!(knowRole.isDirector() || knowRole.isManager()))
            return new ApiResponse("You can't create service!", false);

        boolean existsUssd = serviceRepository.existsByUssdCodeId(serviceDto.getUssdCodeId());
        if (existsUssd)
            return new ApiResponse("This Ussd must not add", false);

        boolean existsService = serviceRepository.existsByNameAndCategoryId(serviceDto.getName(), serviceDto.getCategoryId());
        if (existsService)
            return new ApiResponse("Service already exists!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(serviceDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("category not found!", false);

        Optional<UssdCode> optionalUssdCode = ussdCodeRepository.findById(serviceDto.getUssdCodeId());
        if (optionalUssdCode.isEmpty())
            return new ApiResponse("Ussd code repository!", false);

        Service service = new Service();
        service.setCategory(optionalCategory.get());
        service.setName(serviceDto.getName());
        service.setInfo(serviceDto.getInfo());
        service.setPeriod(serviceDto.getPeriod());
        service.setUssdCode(optionalUssdCode.get());
        serviceRepository.save(service);

        return new ApiResponse("Service added!", true);
    }

    //__GET ALL
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    //__GET ONE
    public Service getService(UUID id) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        return optionalService.orElse(null);
    }


    /*
    * __EDIT
    * */
    public ApiResponse editService(UUID id, ServiceDto serviceDto) {

        if (!(knowRole.isDirector() || knowRole.isManager()))
            return new ApiResponse("You can't edit service", false);

        boolean existsInCategory = serviceRepository.existsByNameAndCategoryIdAndIdNot(serviceDto.getName(), serviceDto.getCategoryId(), id);
        if (existsInCategory)
            return new ApiResponse("This service already exists!", false);

        boolean existsByUssd = serviceRepository.existsByUssdCodeIdAndIdNot(serviceDto.getUssdCodeId(), id);
        if (existsByUssd)
            return new ApiResponse("Ussd code is already busy", false);

        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isEmpty())
            return new ApiResponse("Service not found!", false);

        Optional<UssdCode> optionalUssdCode = ussdCodeRepository.findById(serviceDto.getUssdCodeId());
        if (optionalUssdCode.isEmpty())
            return new ApiResponse("Ussd code not found!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(serviceDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found!", false);

        Service editingService = optionalService.get();
        editingService.setUssdCode(optionalUssdCode.get());
        editingService.setInfo(serviceDto.getInfo());
        editingService.setPeriod(serviceDto.getPeriod());
        editingService.setCategory(optionalCategory.get());
        editingService.setName(serviceDto.getName());
//
        serviceRepository.save(editingService);
        return new ApiResponse("Service edited!", true);
    }


    /*
    * __DELETE__
    * */
    public ApiResponse deleteService(UUID id) {
        if (!(knowRole.isDirector() || knowRole.isManager()))
            return new ApiResponse("You can't delete!", false);

        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isEmpty())
            return new ApiResponse("Service not found!", false);

        serviceRepository.deleteById(id);
        return new ApiResponse("Service deleted!", true);
    }


    /*
    * Popular servieses
    * */
    public List<Service> getAllPopularServices() {
        return null;
    }
}
