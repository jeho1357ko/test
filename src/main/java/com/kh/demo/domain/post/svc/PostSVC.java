package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.web.form.SaveForm;

import java.util.List;
import java.util.Optional;

public interface PostSVC {

  //게시글 목록

  List<Post> findAll();

  // 세부 사항
  Optional<Post> detail(Long id);

  // 게시글 등록
  Long save(SaveForm saveForm);

  // 게시글 삭제 단건
  Long deleteId(Long id);

  // 게시글 여러건 삭제
  int deleteByIds(List<Long> postId);

  int updateById(Long id ,Post post);

}