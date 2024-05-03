package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestParam("name") String name) {
        Board board = new Board();
        board.setName(name);
        Board createdBoard = boardService.createBoard(board);
        return ResponseEntity.ok(createdBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long id, @RequestParam("name") String name) {
        Board board = new Board();
        board.setName(name);
        Board updatedBoard = boardService.updateBoard(id, board);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("id") Long id) {
        Board board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }


}
