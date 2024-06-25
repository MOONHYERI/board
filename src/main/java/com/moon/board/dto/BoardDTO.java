package com.moon.board.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id; // 아이디
    private String boardWriter; // 작성자
    private String boardPass; // 게시글 비밀번호
    private String boardTitle; //게시글 제목
    private String boardContents; // 게시글 내용
    private int boardHits; // 조회수
    private String createdAt; // 작성 시간
    private int fileAttached; // 파일첨부가 되었나 안되었나 판단
    private MultipartFile boardFile; // 게시글 작성할때 파일을 담기위한 필드

}
