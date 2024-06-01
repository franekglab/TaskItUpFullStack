package io.fglab.taskitup.payload;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Password can't be blank")
    private String password;


}
