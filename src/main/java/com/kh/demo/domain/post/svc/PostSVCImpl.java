package com.kh.demo.domain.post.svc;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.dao.PostDAO;
import com.kh.demo.web.form.SaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSVCImpl implements PostSVC{

  final private PostDAO postDAO;
  // 목록
  @Override
  public List<Post> findAll() {

    return postDAO.findAll();
  }
  // 세부 내용
  @Override
  public Optional<Post> detail(Long id) {

    return postDAO.detail(id);
  }
  // 등록
  @Override
  public Long save(SaveForm saveForm) {
    Post post = new Post();
    post.setName(saveForm.getName());
    post.setTitle(saveForm.getTitle());
    post.setContent(saveForm.getContent());



    return postDAO.save(post);
  }
  //단건삭제
  @Override
  public Long deleteId(Long id) {
    return postDAO.deleteId(id);
  }
  //일괄 삭제
  @Override
  public int deleteByIds(List<Long> postId) {
    return postDAO.deleteByIds(postId);
  }
  // 수정
  @Override
  public int updateById(Long id, Post post) {
    return postDAO.updateById( id,  post);
  }
}