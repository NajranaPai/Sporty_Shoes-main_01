package com.sporty_shoes.controller;


import com.sporty_shoes.model.Category;
import com.sporty_shoes.model.Product;
import com.sporty_shoes.model.User;
import com.sporty_shoes.repository.UserRepository;
import com.sporty_shoes.service.CategoryService;
import com.sporty_shoes.service.ProductService;
import com.sporty_shoes.dto.ProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;





@Controller
public class AdminController {

    // getting directory for saving images
    public static String productImageUploadDir = System.getProperty("user.dir") + 
                         "/src/main/resources/static/productImages";



    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
     // products section
    
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.getAlProducts());
        return "products";
    }
    
    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("categories", this.categoryService.allCategories());
        model.addAttribute("productDTO", new ProductDTO() );
        return "productsAdd";
    }

    // for adding and updating products
     @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute("productDTo") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile file, 
                                  @RequestParam("imgName") String  imgName  ) throws IOException{
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(this.categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setWeight(productDTO.getWeight());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(file.isEmpty()){
            imageUUID = imgName;
        }else{
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(productImageUploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }
        product.setImageName(imageUUID);
        this.productService.addProduct(product);

        return "redirect:/admin/products";
    }
   
    @GetMapping(value="/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        this.productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping(value="/admin/products/update/{id}")
    public String updateProduct(Model model, @PathVariable("id") long id) {
        Product product = this.productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setWeight(product.getWeight());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", this.categoryService.allCategories());
        return "productsAdd";
    }


    // category section
    


    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        List<Category> categories = this.categoryService.allCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category){
        this.categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    // using get to delte beacuse this isn't rest api
    @GetMapping(value="/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        this.categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping(value="/admin/categories/update/{id}")
    public String updateCategory(@PathVariable("id") int id, Model model) {
        Optional<Category> category = this.categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else{
            return "errorPage";
        }
    }

    @GetMapping(value = "/admin/changePassword")
    public String getChangePassword(){
      return "changePassword";
    }

    @PostMapping(value = "/admin/changePassword")
     public String postChangePassword(Authentication authentication, 
                                  @RequestParam("oldPassword") String oldPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  Model model){
                                    
    String email = authentication.getName();
    User user = this.userRepository.findUserByEmail(email).get();
    boolean isOldPasswordCorrect = this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    if(isOldPasswordCorrect){
         user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
         this.userRepository.save(user);
         model.addAttribute("success", true);
    }else{
        model.addAttribute("error", true);
    }
      return "changePassword";
    }

}
