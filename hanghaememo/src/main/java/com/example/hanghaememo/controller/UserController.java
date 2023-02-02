package com.example.hanghaememo.controller;

import com.example.hanghaememo.dto.RegisterRequestDto;

import com.example.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.hanghaememo.entity.User;
import com.example.hanghaememo.dto.LoginRequestDto;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/api/auth/signup")
    // @RequestBody 가뭘까

    public String registerUser(@RequestBody RegisterRequestDto registerRequestDto)
    {
            if(userService.checkRegister(registerRequestDto))
            {
                System.out.println(userService.regster(registerRequestDto).getUsername());
            }
            else {
                throw new IllegalArgumentException("회원가입에 실패했습니다");
            }
            return "회원가입이 완료되었습니다.";
    }
    @ResponseBody
    @PostMapping("/api/auth/login")
    public String loginUser(@RequestBody LoginRequestDto loginRequestDto,HttpServletResponse response)
    {
        userService.login(loginRequestDto,response);
        return "success";
    }

}
