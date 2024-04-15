package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        return ResponseEntity.ok(boardService.createBoard(board));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody Board board) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, board));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }
}
