package com.kh.demo.web.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {
  @NotBlank(message = "이메일은 필수!")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수!")
  @Size(min = 7, message = "비밀번호는 7자 이상이어야 합니다.")
  private String passwd;
}