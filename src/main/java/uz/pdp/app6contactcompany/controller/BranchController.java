package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.Branch;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.BranchDto;
import uz.pdp.app6contactcompany.service.BranchService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    @Autowired
    BranchService branchService;

    @PostMapping
    public HttpEntity<?> addBranch(@Valid @RequestBody BranchDto branchDto){
        ApiResponse apiResponse = branchService.addBranch(branchDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    //FOR DIRECTOR
    @GetMapping
    public HttpEntity<?> getAllBranches(){
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    //Filiallar manageri , filial rahbari va direktor uchun
    @GetMapping("/{id}")
    public HttpEntity<?> getBranch(@PathVariable Integer id){
        Branch branch = branchService.getBranch(id);
        return ResponseEntity.status(branch == null ? 409 : 200).body(branch);
    }

    //faqat direktor
    @PutMapping("/{id}")
    public HttpEntity<?> editBranch(@PathVariable Integer id, @RequestBody BranchDto branchDto){
        ApiResponse apiResponse = branchService.editBranch(id, branchDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    //faqat direktor
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBranch(@PathVariable Integer id){
        ApiResponse apiResponse = branchService.deleteBranch(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
