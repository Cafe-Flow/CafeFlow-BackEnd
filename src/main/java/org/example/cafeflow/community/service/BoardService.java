package org.example.cafeflow.community.service;

import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.repository.BoardRepository;
import org.example.cafeflow.community.repository.PostRepository;
import org.example.cafeflow.exception.BoardIntegrityViolationException;
import org.example.cafeflow.exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

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
        Board board = this.getBoard(boardId);
        try {
            boardRepository.delete(board);
        } catch (DataIntegrityViolationException ex) {
            throw new BoardIntegrityViolationException("게시판 삭제 중 데이터 무결성 문제 발생: " + ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시판을 찾을 수 없습니다. ID: " + boardId));
    }
}
