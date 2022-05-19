package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.Service;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.ServiceDto;
import uz.pdp.app6contactcompany.service.ServiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;




    /**
    * Manager, Director
    * */
    @PostMapping
    public HttpEntity<?> addService(@RequestBody ServiceDto serviceDto){
        ApiResponse apiResponse = serviceService.addService(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /**
    * hamma uchun
    * */
    @GetMapping
    public HttpEntity<?> getAllServices(){
        List<Service> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    /**
     * Hamma uchun
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getService(@PathVariable UUID id){
        Service service = serviceService.getService(id);
        return ResponseEntity.ok(service);
    }

    /**
    * Director va managerlar
    * */
    @PutMapping("/{id}")
    public HttpEntity<?> editService(@PathVariable UUID id, @RequestBody ServiceDto serviceDto){
        ApiResponse apiResponse = serviceService.editService(id, serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     * __Delete
     * director va manager
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteService(@PathVariable UUID id){
        ApiResponse apiResponse = serviceService.deleteService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}





