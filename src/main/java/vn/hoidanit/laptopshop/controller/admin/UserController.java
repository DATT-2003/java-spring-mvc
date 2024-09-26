package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.io.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;

    public UserController(UserService userService, UserRepository useRepository, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUserByEmail("dattien1152003@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("Page", "test");
        model.addAttribute("Test1", "Modal");
        return "Page_1";// Tên trong webapp
        // được config đuôi jps trong file config WebMvcConfig.java
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "/admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") User inforUser,
            @RequestParam("file") MultipartFile file) {
        String avatar = this.uploadService.handalSaveUloadFile(file, "avatar");
        // this.userService.handleSaveUser(inforUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdatePage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("admin/user/update")
    public String postUpdate(Model model, @ModelAttribute("newUser") User inforUser) {
        User currentUser = this.userService.getUserById(inforUser.getId());
        if (currentUser != null) {
            currentUser.setAddress(inforUser.getAddress());
            currentUser.setFullName(inforUser.getFullName());
            currentUser.setPhone(inforUser.getPhone());
            this.userService.handalSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User infoUser) {
        this.userService.deleteUserById(infoUser.getId());
        return "redirect:/admin/user";
    }

}
