package org.example.cafeflow.community.service;

import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Long boardId, Board boardDetails) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
        board.setName(boardDetails.getName());
        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
}
