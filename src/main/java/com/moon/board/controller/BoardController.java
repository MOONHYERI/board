package com.moon.board.controller;

import com.moon.board.dto.BoardDTO;
import com.moon.board.dto.BoardFileDTO;
import com.moon.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor // final이 붙은 필드만 가지고 생성자를 생성하기 위한 어노테이션
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save") // 주소
    public String save() { // 매서드
        return "save"; // 글쓰기 폼으로 이동하는 view 이름
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO) throws IOException { // 위의 매서드랑 이름이 같지만 parameter 가 다르기 때문에 가능하다
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO); // 게시글 저장
        return "redirect:/list"; // redirect:재요청. 화면을 띄우는게 아니라 다시 list를 요청
    }

    @GetMapping("/list")
    public String findAll(Model model) { // Model객체 : 스프링프레임워크에서 제공하는 인터페이스. 데이터를 화면으로 가져갈 수 있도록 전달해주는 객체
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList); // 모델에 게시글 목록 추가
        System.out.println("boardDTOList = " + boardDTOList);
        return "list"; // 게시글 목록을 보여주는 view 이름
    }

    // /10, /1
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        /*System.out.println("Updating hits for id = " + id);*/
        // 조회수 처리
        boardService.updateHits(id);
        // 상세내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO); // 모델에 게시글 상세 정보 추가
        System.out.println("boardDTO = " + boardDTO);
        if (boardDTO.getFileAttached() == 1) {// 파일이 있으면 그 내용을 가지고온다
            BoardFileDTO boardFileDTO = boardService.findFile(id);
            model.addAttribute("boardFile", boardFileDTO);
        }
        return "detail"; // 게시글 상세 페이지를 보여주는 view 이름
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO); // 게시글 업데이트
        BoardDTO dto = boardService.findById(boardDTO.getId()); // 업데이트 된 게스글 조회
        model.addAttribute("board", dto); // 모델에 게시글 추가
        return "detail"; // list로 바꿔도 상관없다
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }
}






















