package org.koreait.restcontrollers.board;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("apiBoardController")
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardSaveService saveService;

    @PostMapping
    public ResponseEntity<Object> write(@RequestBody BoardForm form) {

        saveService.save(form);

        // 등록 성공
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody BoardForm form) {

        return null;
    }
}
