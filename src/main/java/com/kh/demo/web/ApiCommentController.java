package com.kh.demo.web;

import com.kh.demo.domain.comment.svc.CommentSVC;
import com.kh.demo.domain.entity.Comment;
import com.kh.demo.domain.post.svc.PostSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.comment.ApiResponseCode;
import com.kh.demo.web.api.comment.SaveApi;
import com.kh.demo.web.api.comment.UpdateApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class ApiCommentController {

  final private CommentSVC commentSVC;

  // 등록
 @PostMapping
  public ResponseEntity<ApiResponse<Comment>> add(
      @RequestBody  SaveApi saveApi
      ){
    Comment comment = new Comment();
    BeanUtils.copyProperties(saveApi,comment);
    Long id = commentSVC.save(saveApi.getPostId(), comment);

    Optional<Comment> listComments = commentSVC.detail(id);
   Comment detailcomment = listComments.orElseThrow();

   ApiResponse<Comment> commentApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS,detailcomment);

    return ResponseEntity.status(HttpStatus.CREATED).body(commentApiResponse);
  }

  // 목록
  @GetMapping("/paging")
  public  ResponseEntity<ApiResponse<List<Comment>>> findAll(
      @RequestParam (value = "pageNo" , defaultValue = "1"  ) Integer pageNo,
      @RequestParam (value = "numOfRows" , defaultValue = "10") Integer numOfRows,
      @RequestParam (value = "pid") Integer pid
  ){
    List<Comment> commentList = commentSVC.findAll(pid ,pageNo, numOfRows);

    int totalCount = commentSVC.getTotalCount(pid);

    ApiResponse<List<Comment>> listApiResponse = ApiResponse.of(
        ApiResponseCode.SUCCESS,
        commentList,
        new ApiResponse.Paging(pageNo,numOfRows,totalCount)
        );

    return ResponseEntity.ok(listApiResponse);
  }

  //삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Comment>> deleteById(@PathVariable("id") Long id){

    Optional<Comment> optionalComment = commentSVC.detail(id);
    Comment comment = optionalComment.orElseThrow(
        ()-> new NoSuchElementException("상품이 없습니다")
    );

    String delete = commentSVC.delete(id);
    ApiResponse<Comment> commentApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, comment);


    return ResponseEntity.ok(commentApiResponse);
  }

  //수정

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<Comment>> update(@PathVariable("id") Long id,
                                                     @RequestBody UpdateApi updateApi
                                                     ){
    Optional<Comment> optionalComment = commentSVC.detail(id);
    Comment comment = optionalComment.orElseThrow(
        () -> new NoSuchElementException("없는 상품입니다.")
    );
    Comment co = new Comment();
    BeanUtils.copyProperties(updateApi,co);
    Long update = commentSVC.update(id, co);

    optionalComment = commentSVC.detail(id);
    Comment updateComment = optionalComment.orElseThrow();

    ApiResponse<Comment> commentApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS,updateComment);

    return ResponseEntity.ok(commentApiResponse);
  }



}
