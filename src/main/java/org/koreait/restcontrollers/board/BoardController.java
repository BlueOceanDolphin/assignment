package org.koreait.restcontrollers.board;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.rests.JSONData;
import org.koreait.controllers.BoardForm;
import org.koreait.entities.BoardData;
import org.koreait.models.board.BoardDeleteService;
import org.koreait.models.board.BoardInfoService;
import org.koreait.models.board.BoardListService;
import org.koreait.models.board.BoardSaveService;
import org.koreait.repositories.BoardDataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("apiBoardController")
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardSaveService saveService;
    private final BoardInfoService infoService;
    private final BoardDeleteService deleteService;
    private final BoardListService listService;

    @PostMapping
    public ResponseEntity<Object> write(@RequestBody BoardForm form) {

        saveService.save(form);

        // 등록 성공
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@RequestBody BoardForm form) {

        saveService.save(form);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<JSONData<Object>> view(@PathVariable Long id) {

        BoardData boardData = infoService.get(id);

        JSONData<Object> data = JSONData
                .builder()
                .success(true)
                .data(boardData)
                .build();

        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        deleteService.delete(id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() {

        List<BoardData> data = listService.gets();

        JSONData<Object> data1 = JSONData
                .builder()
                .success(true)
                .data(data)
                .build();

        return ResponseEntity.ok(data1);
    }
}
