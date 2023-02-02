package com.example.hanghaememo.dto;
import com.example.hanghaememo.entity.UserRoleEnum;
import com.example.hanghaememo.entity.User;
import lombok.Getter;
import lombok.Setter;

import static com.example.hanghaememo.entity.UserRoleEnum.ADMIN;

@Setter
@Getter
public class RegisterRequestDto {
    private String userName;
    private String pwd;
    private boolean admin = false;
    private String adminToken = "";

    public boolean isAdmin(UserRoleEnum userRole)
    {
        if(userRole.equals(ADMIN))
        {
            return true;
        }
        return false;
    }
}
