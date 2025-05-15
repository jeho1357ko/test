package com.kh.demo.domain.post.dao;

import com.kh.demo.domain.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostDAOImplTest {

  @Autowired
  PostDAO postDAO;

  @Test
  void testLog() {

    log.info("hi");
    log.warn("warn");
    log.error("error");
  }

  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Post> list = postDAO.findAll();

    for (Post post : list) {
      log.info("post={}", post);
    }
  }
  @Test
  @DisplayName("상품세부내용")
  void detail() {
    Long postId = 1L;
    Optional<Post> optionalPost = postDAO.detail(postId);
    Post findedPost = optionalPost.orElseThrow();// 값이 없으면 예외 발생
    log.info("findedProduct={}", findedPost);
  }

  @Test
  @DisplayName("상품저장")
  void save() {
    Post post = new Post();
    post.setContent("테스트2");
    post.setTitle("111");
    post.setName("@@@");

    Long pid = postDAO.save(post);
    log.info("상품번호={}", pid);
  }

  @Test
  @DisplayName("상품e단건삭제")
  void deleteId() {
    Long id = 21l;
    long rows = postDAO.deleteId(id);
    log.info("rows={}",rows);
    Assertions.assertThat(rows).isEqualTo(1);

  }

  @Test
  @DisplayName("상품여러건삭제")
  void deleteByIds() {
    List<Long> ids = List.of(22L,23L);
    int rows = postDAO.deleteByIds(ids);
    Assertions.assertThat(rows).isEqualTo(2);
  }


  @Test
  @DisplayName("상품수정")
  void updateById() {
    Long id = 24L;
    Post post = new Post();
    post.setName("본체");
    post.setTitle("1L");
    post.setContent("3_000_000L");

    int rows = postDAO.updateById(id, post);

    Optional<Post> optPost = postDAO.detail(id);

    Post modifiedPost = optPost.orElseThrow();

    Assertions.assertThat(modifiedPost.getName()).isEqualTo("본체");
    Assertions.assertThat(modifiedPost.getTitle()).isEqualTo("1L");
    Assertions.assertThat(modifiedPost.getContent()).isEqualTo("3_000_000L");

  }
}