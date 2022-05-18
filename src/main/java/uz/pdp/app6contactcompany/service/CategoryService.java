package uz.pdp.app6contactcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app6contactcompany.entity.Category;
import uz.pdp.app6contactcompany.my.KnowRole;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.CategoryDto;
import uz.pdp.app6contactcompany.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    KnowRole knowRole;

    //__CREATE
    public ApiResponse addCategory(CategoryDto categoryDto) {
        if (!(knowRole.isDirector() || knowRole.isManager()))
            return new ApiResponse("You can't add category", false);

        boolean exists = categoryRepository.existsByNameAndCategoryId(categoryDto.getName(), categoryDto.getCategoryId());
        if (exists)
            return new ApiResponse("This category already exists!", false);

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("Parent category not found!", false);

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setCategory(optionalCategory.get());
        categoryRepository.save(category);

        return new ApiResponse("Category added by " + knowRole.getAuthUser().getEmail(), true);
    }

    //__READ ALL
    public List<Category> getAllCategory() {
        if (knowRole.isDirector() || knowRole.isManager())
            return categoryRepository.findAll();
        return null;
    }

    //__READ ONE
    public Category getCategory(UUID id) {
        if (knowRole.isDirector() || knowRole.isManager()) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent())
                return optionalCategory.get();
        }
        return null;
    }

    //__UPDATE
    public ApiResponse editCategory(UUID id, CategoryDto categoryDto) {

        if (!(knowRole.isDirector() || knowRole.isManager()))
            return new ApiResponse("You can't add category", false);

        

    }
}
