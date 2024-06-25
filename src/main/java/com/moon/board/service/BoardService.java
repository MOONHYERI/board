package com.moon.board.service;

import com.moon.board.dto.BoardDTO;
import com.moon.board.dto.BoardFileDTO;
import com.moon.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().isEmpty()) {
            // 파일 없다.
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // 파일 있다.
            boardDTO.setFileAttached(1);
            // 게시글 저장 후 id값 활용을 위해 리턴 받음
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일만 따로 가져오기
            MultipartFile boardFile = boardDTO.getBoardFile();
            // 파일 이름 가져오기
            String originalFilename = boardFile.getOriginalFilename();
            System.out.println("originalFileName = " + originalFilename);
            // 저장용 이름 만들기
            System.out.println(System.currentTimeMillis()); // UTC 기준으로 얼마나 밀리초가 지났는지 주는 메서드
            String storeFileName = System.currentTimeMillis() + "-" + originalFilename;
            System.out.println("storeFileName = " + storeFileName);
            // BoardFileDTO 세팅
            BoardFileDTO boardFileDTO = new BoardFileDTO();
            boardFileDTO.setOriginalFileName(originalFilename); // 오리지날 네임
            boardFileDTO.setStoredFileName(originalFilename); // 스토어드 네임
            boardFileDTO.setBoardId(savedBoard.getId()); // 아이디값
            // 파일 저장용 폴더에 파일 저장 처리
            String savePath = "C:/Users/Owner/Desktop/사진/" + storeFileName;
            boardFile.transferTo(new File(savePath)); // 전달
            // board_file_table 저장처리
            boardRepository.saveFile(boardFileDTO);
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void updateHits(Long id) {
        /*System.out.println("Updating hits in service for id :" + id);*/
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public BoardFileDTO findFile(Long id) {
        return boardRepository.fineFile(id);
    }
}





















