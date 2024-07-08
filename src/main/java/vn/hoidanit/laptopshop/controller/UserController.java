package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handalHello();
        model.addAttribute("Page", test);
        model.addAttribute("Test1", "Modal");
        return "Page_1";
    }
}

// @RestController
// public class UserController {

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handalHello();
// }
// }
