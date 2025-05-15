package com.kh.demo.domain.post.dao;

import com.kh.demo.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
  // 게시글 목록
  List<Post> findAll();


  // 게시글 세부 내용
  Optional<Post> detail(Long postId);

  // 게시글 등록
  Long save(Post post);

  // 단건 삭제
  Long deleteId(Long id);

  // 여러건 삭제
  int deleteByIds(List<Long> postId);

  //수정
  int updateById(Long id, Post post);
}

