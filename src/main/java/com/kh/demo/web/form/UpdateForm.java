package com.kh.demo.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateForm {
  private  Long postId;

  @NotBlank(message = "제목은 필수입니다.")
  @Size(min=1, max=20 , message = "제목은 20자리를 넘길수없습니다.")
  private  String title;

  @NotBlank(message = "내용은 필수입니다.")
  private  String content;

  @NotBlank(message = "이름은 필수입니다.")
  @Size(min=1, max=6 , message = "이름은 6자리를 넘길수없습니다.")
  private  String name;

  private java.time.LocalDateTime revisedDate;
  private java.time.LocalDateTime dateCreated;
}
