package com.kh.demo.web;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.svc.PostSVC;
import com.kh.demo.web.form.DetailForm;
import com.kh.demo.web.form.SaveForm;
import com.kh.demo.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

  final  private PostSVC postSVC;
  @GetMapping
  public String findAll(Model model){
    List<Post> list = postSVC.findAll();
    model.addAttribute("list",list);
    return "/post/main";
  }

  // 동록화면
  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("saveForm", new SaveForm());
    return "post/add";
  }

  // 세부내용
  @GetMapping("/{id}")
  public  String detail(@PathVariable("id") Long id,        // 경로변수 값을 읽어올때
                        Model model){

    Optional<Post> optionalProduct = postSVC.detail(id);
    Post findedPost = optionalProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setPostId(findedPost.getPostId());
    detailForm.setName(findedPost.getName());
    detailForm.setTitle(findedPost.getTitle());
    detailForm.setContent(findedPost.getContent());
    detailForm.setDateCreated(findedPost.getDateCreated());
    detailForm.setRevisedDate(findedPost.getRevisedDate());

    model.addAttribute("detailForm",detailForm);
    return "/post/detail";
  }

  // 게시글 등록
  @PostMapping("/add")
  public  String save(@Valid @ModelAttribute SaveForm saveForm ,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes){

    if (bindingResult.hasErrors()) {

      return "post/add";
    }

    Long saveId = postSVC.save(saveForm);
    redirectAttributes.addAttribute("id",saveId);

    return "redirect:/posts/{id}";
  }

  //단건 삭제
  @GetMapping("/{id}/del")
  public  String deleteId(@PathVariable("id") Long id){

    postSVC.deleteId(id);
    return "redirect:/posts";
  }

  // 여러건 삭제
  @PostMapping("/del")
  public  String deleteByIds(@RequestParam("postId") List<Long> postId){

    int rows = postSVC.deleteByIds(postId);
    return "redirect:/posts";
  }

  //수정화면
  @GetMapping("/{id}/edit")
  public  String update(@PathVariable("id") Long postId,
                        Model model){
    Optional<Post> optionalPost = postSVC.detail(postId);
    Post findedPost = optionalPost.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setPostId(findedPost.getPostId());
    updateForm.setTitle(findedPost.getTitle());
    updateForm.setName(findedPost.getName());
    updateForm.setContent(findedPost.getContent());
    updateForm.setRevisedDate(findedPost.getRevisedDate());
    updateForm.setDateCreated(findedPost.getDateCreated());


    model.addAttribute("updateForm", updateForm);
    return "post/update";
  }
  //수정
  @PostMapping("/{id}/edit")
  public String updateById(@PathVariable("id") Long id,
                           @Valid @ModelAttribute UpdateForm updateForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "post/update";
    }

    Post post = new Post();
    post.setPostId(updateForm.getPostId());
    post.setTitle(updateForm.getTitle());
    post.setContent(updateForm.getContent());
    post.setName(updateForm.getName());
    post.setRevisedDate(LocalDateTime.now());


    int rows = postSVC.updateById(id,post);
    redirectAttributes.addAttribute("id",id);
    return "redirect:/posts/{id}";
  }
}
