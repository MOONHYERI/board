package com.moon.board.repository;

import com.moon.board.dto.BoardDTO;
import com.moon.board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final SqlSessionTemplate sql; // mybatis 에서 제공하는 클래스. mapper 호출
    public BoardDTO save(BoardDTO boardDTO) {
        sql.insert("Board.save", boardDTO);// board는 mapper의 namespace.save는 쿼리문을 담고있는 태그들 , parameter
        return boardDTO;
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

    public BoardFileDTO fineFile(Long id) {
        return sql.selectOne("Board.findFile", id);
    }
}























