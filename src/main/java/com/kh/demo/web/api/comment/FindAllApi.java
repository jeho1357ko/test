package com.kh.demo.web.api.comment;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FindAllApi {
  private Long commentId ;
  private String name;
  private String content;
  private LocalDateTime createdTime;
}
