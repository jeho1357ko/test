package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
  private Long commentId ;
  private String name;
  private String content;
  private LocalDateTime createdTime;
  private Long postId;
}
