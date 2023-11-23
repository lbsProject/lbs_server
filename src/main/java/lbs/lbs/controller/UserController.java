package lbs.lbs.controller;

import lbs.lbs.config.auth.PrincipalDetails;
import lbs.lbs.dto.UserRequestDto;
import lbs.lbs.entity.User;
import lbs.lbs.repository.UserRepository;
import lbs.lbs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @GetMapping({"","/"})
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "joinForm";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "어드민 페이지입니다.";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "유저 페이지입니다.";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(UserRequestDto userRequestDto) {

        return userService.createUser(userRequestDto);
    }
}







