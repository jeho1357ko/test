package com.kh.demo.web.login;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JoinForm {
  @NotBlank(message = "이메일은 필수!")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수!")
  @Size(min = 7, message = "비밀번호는 7자 이상이어야 합니다.")
  private String passwd;

  @NotBlank(message = "비밀번호 확인은 필수!")
  private String passwdConfirm;

  @NotBlank(message = "전화번호는 필수!")
  private String tel;

  @NotBlank(message = "닉네임은 필수!")
  private String nickname;

  @NotBlank(message = "성별은 필수!")
  private String gender;

  @NotBlank(message = "취미는 필수!")
  private String hobby;

  @NotBlank(message = "지역은 필수!")
  private String region;
}