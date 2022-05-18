package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Branch;
import uz.pdp.app6contactcompany.entity.Region;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.BranchDto;
import uz.pdp.app6contactcompany.repository.BranchRepository;
import uz.pdp.app6contactcompany.repository.RegionRepository;
import uz.pdp.app6contactcompany.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    KnowRole knowRole;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegionRepository regionRepository;


    /*
    * __CREATE__
    * */
    //Add Branch by DIRECTOR
    public ApiResponse addBranch(BranchDto branchDto) {

        //faqat direktor filial qo'shaoladi
        if (!knowRole.isDirector())
            return new ApiResponse("You cannot add branch!", false);

        //bitta viloyatta bir xil nomli ikkita filial bo'lmaydi
        boolean exists = branchRepository.existsByNameAndRegion_Id(branchDto.getName(), branchDto.getRegionId());
        if (exists)
            return new ApiResponse("This branch already exists!", false);


        //Rahbarni id orqali bazadan olish
        Optional<User> optionalUserLeader = userRepository.findById(branchDto.getLeaderId());
        if (optionalUserLeader.isEmpty())
            return new ApiResponse("Leader not found!", false);

        //agar olingan rahbarning rollari orasida BRANCH_LEADER degan roli bolmasa u rahbar emas va filialga rahbarlik qilaolmaydi
        if (!knowRole.isBranchLeader(optionalUserLeader.get().getRoles()))
            return new ApiResponse("Not Leader", false);


        //Managerni id orqali bazadan olish
        Optional<User> optionalUserManager = userRepository.findById(branchDto.getManagerId());
        if (optionalUserManager.isEmpty())
            return new ApiResponse("Manager not found!", false);

        //agar olingan managerning rollari orasida BRANCH_MANAGER degan roli bolmasa u manager emas va filialga managerlik qilaolmaydi
        if (!(knowRole.isBranchManager(optionalUserManager.get().getRoles())))
            return new ApiResponse("Not Manager", false);


        //agar tanlangan viloyat topilmasa . . .
        Optional<Region> optionalRegion = regionRepository.findById(branchDto.getRegionId());
        if (optionalRegion.isEmpty())
            return new ApiResponse("Region not found!", false);

        //Manager va Rahbar
        User userBranchLeader = optionalUserLeader.get();
        User userBranchManager = optionalUserManager.get();

        Branch branch = new Branch();
        branch.setLeader(userBranchLeader);
        branch.setName(branchDto.getName());
        branch.setRegion(optionalRegion.get());
        branch.setManager(userBranchManager);
        Branch savedBranch = branchRepository.save(branch);

        //rahbar va managerga filial biriktiriladi
        userBranchLeader.setBranch(branch);
        userBranchManager.setBranch(branch);
        userRepository.save(userBranchLeader);
        userRepository.save(userBranchManager);

        return new ApiResponse("Branch added successfully!", true);
    }


    /*
    * __READ__
    * */
    //DIRECTOR, FILIAL_MANAGER barcha filiallarni koraoladi
    public List<Branch> getAllBranches() {
        if (knowRole.isDirector() || knowRole.isBranchManager())
            return branchRepository.findAll();
        return null;
    }

    //DIRECTOR, FILIAL_MANAGER, BRANCH_LEADER(o'ziga tegishlisini) - bitta filialni koraoladi
    public Branch getBranch(Integer id) {
        if (knowRole.isDirector() || knowRole.isBranchManager() || (knowRole.getAuthUser().getBranch().getId().equals(id))) {
            Optional<Branch> optionalBranch = branchRepository.findById(id);
            return optionalBranch.orElse(null);
        }
        return null;
    }


    /*
    * __EDITE__
    * */
    public ApiResponse editBranch(Integer id, BranchDto branchDto) {
        //faqat direktor filialni tahrirlay oladi
        if (!knowRole.isDirector())
            return new ApiResponse("You cannot edit branch!", false);

        //bitta viloyatta bir xil nomli ikkita filial bo'lmaydi
        boolean exists = branchRepository.existsByNameAndRegion_IdAndIdNot(branchDto.getName(), branchDto.getRegionId(), id);
        if (exists)
            return new ApiResponse("This branch already exists!", false);


        //Rahbarni id orqali bazadan olish
        Optional<User> optionalUserLeader = userRepository.findById(branchDto.getLeaderId());
        if (optionalUserLeader.isEmpty())
            return new ApiResponse("Leader not found!", false);

        //agar olingan rahbarning rollari orasida BRANCH_LEADER degan roli bolmasa u rahbar emas va filialga rahbarlik qilaolmaydi
        if (!knowRole.isBranchLeader(optionalUserLeader.get().getRoles()))
            return new ApiResponse("Not Leader", false);


        //Managerni id orqali bazadan olish
        Optional<User> optionalUserManager = userRepository.findById(branchDto.getManagerId());
        if (optionalUserManager.isEmpty())
            return new ApiResponse("Manager not found!", false);

        //agar olingan managerning rollari orasida BRANCH_MANAGER degan roli bolmasa u manager emas va filialga managerlik qilaolmaydi
        if (!(knowRole.isBranchManager(optionalUserManager.get().getRoles())))
            return new ApiResponse("Not Manager", false);


        //agar tanlangan viloyat topilmasa . . .
        Optional<Region> optionalRegion = regionRepository.findById(branchDto.getRegionId());
        if (optionalRegion.isEmpty())
            return new ApiResponse("Region not found!", false);

        //Manager va Rahbar
        User userBranchLeader = optionalUserLeader.get();
        User userBranchManager = optionalUserManager.get();

        Branch branch = new Branch();
        branch.setLeader(userBranchLeader);
        branch.setName(branchDto.getName());
        branch.setRegion(optionalRegion.get());
        branch.setManager(userBranchManager);
        Branch savedBranch = branchRepository.save(branch);

        //rahbar va managerga filial biriktiriladi
        userBranchLeader.setBranch(branch);
        userBranchManager.setBranch(branch);
        userRepository.save(userBranchLeader);
        userRepository.save(userBranchManager);

        return new ApiResponse("Branch successfully edited!", true);
    }


    /*
    * __DELETE__
    * */
    public ApiResponse deleteBranch(Integer id) {

        if (!knowRole.isDirector())
            return new ApiResponse("You cannot delete branch", false);

        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty())
            return new ApiResponse("Branch not found!", false);

        branchRepository.deleteById(id);
        return new ApiResponse("Branch deleted!", true);
    }

}
