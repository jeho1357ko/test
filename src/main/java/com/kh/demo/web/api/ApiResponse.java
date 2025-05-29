package com.kh.demo.web.api;

import com.kh.demo.web.api.comment.ApiResponseCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
@ToString
public class ApiResponse<T> {
  private final Header header;
  private final T body;
  private final Paging paging;

  private  ApiResponse(Header header, T body){
    this.header = header;
    this.body = body;
    this.paging = null;
  }

  private ApiResponse(Header header, T body,Paging paging){
    this.header = header;
    this.body = body;
    this.paging = paging;
  }
  @Getter
  @ToString
  private static class Header {
    private  final String rtcd;
    private  final String rtmsg;
    private  final Map<String,String> details;

    Header(String rtcd, String rtmsg , Map<String,String> details){
      this.rtcd = rtcd;
      this.rtmsg = rtmsg;
      this.details = details;
    }


  }
  @Getter
  @ToString
  public  static class  Paging{
    private  int numOfRows;
    private  int pageNo;
    private  int totalCount;

    public  Paging(int pageNo , int numOfRows , int totalCount){
      this.pageNo = pageNo;
      this.numOfRows = numOfRows;
      this.totalCount = totalCount;
    }
  }

  public static <T> ApiResponse<T> of(ApiResponseCode responseCode, T body) {
    return new ApiResponse<>(new Header(responseCode.getRtcd(), responseCode.getRtmsg(), null), body);
  }
  public static <T> ApiResponse<T> of(ApiResponseCode responseCode, T body, Paging paging) {
    return new ApiResponse<>(new Header(responseCode.getRtcd(), responseCode.getRtmsg(), null), body, paging);
  }

  // API 응답 생성 메소드-상세 오류 포함
  public static <T> ApiResponse<T> withDetails(ApiResponseCode responseCode, Map<String, String> details, T body) {
    return new ApiResponse<>(new Header(responseCode.getRtcd(), responseCode.getRtmsg(), details), body);
  }

  public static <T> ApiResponse<T> withDetails(ApiResponseCode responseCode, Map<String, String> details, T body, Paging paging) {
    return new ApiResponse<>(new Header(responseCode.getRtcd(), responseCode.getRtmsg(), details), body, paging);
  }



}
