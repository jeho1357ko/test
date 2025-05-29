package com.kh.demo.web.api.comment;

import lombok.Data;

@Data
public class SaveApi {
  private String name;
  private String content;
  private Long postId;
}
