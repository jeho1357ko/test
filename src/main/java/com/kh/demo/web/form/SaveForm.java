package com.kh.demo.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveForm {
  @NotBlank(message = "이름은 필수입니다.")
  @Size(min=1, max=6 , message = "이름은 1자리 이상 6자리 이하여야 합니다.")
  private String name;

  @NotBlank(message = "제목은 필수입니다.")
  @Size(min=1, max=20 , message = "제목은 1자리 이상 20자리 이하여야 합니다.")
  private String title;


  @NotBlank(message = "내용은 필수입니다.")
  private  String content;
}