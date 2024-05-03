package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.BoardDto;
import org.example.cafeflow.community.dto.BoardIntoPostDto;
import org.example.cafeflow.community.dto.BoardToPostDto;
import org.example.cafeflow.community.dto.PostDto;
import org.example.cafeflow.community.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}/posts")
    public ResponseEntity<BoardToPostDto> getAllPostsInBoard(@PathVariable("id") Long id) {
        List<Post> posts = boardService.getAllPostsInBoard(id);
        List<BoardIntoPostDto> postDtos = posts.stream()
                .map(post -> {
                    BoardIntoPostDto postDTO = new BoardIntoPostDto();
                    postDTO.setId(post.getId());
                    postDTO.setBoardId(post.getBoard().getId());
                    postDTO.setTitle(post.getTitle());
                    postDTO.setContent(post.getContent());
                    postDTO.setAuthorNickname(post.getAuthor().getNickname());
                    postDTO.setCreatedAt(post.getCreatedAt());
                    postDTO.setUpdatedAt(post.getUpdatedAt());
                    postDTO.setStateId(post.getState().getId());
                    postDTO.setStateName(post.getState().getName());
                    postDTO.setLikesCount(post.countLikes());
                    postDTO.setViews(post.getViews());
                    postDTO.setCommentCount(post.getComments().size());
                    return postDTO;
                })
                .collect(Collectors.toList());
        BoardToPostDto boardToPostDto = new BoardToPostDto();
        boardToPostDto.setPosts(postDtos);
        return ResponseEntity.ok(boardToPostDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable("id") Long id) {
        Board board = boardService.getBoard(id);
        BoardDto boardDTO = new BoardDto(board.getId(), board.getName());
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        List<BoardDto> boardDtos = boards.stream()
                .map(board -> new BoardDto(board.getId(), board.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(boardDtos);
    }



}
