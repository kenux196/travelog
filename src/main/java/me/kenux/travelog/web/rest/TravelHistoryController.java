package me.kenux.travelog.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.service.TravelHistoryService;
import me.kenux.travelog.web.dto.TravelHistoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/my-travel")
@RequiredArgsConstructor
@Slf4j
public class TravelHistoryController {

    private final TravelHistoryService travelHistoryService;

    @PostMapping
    public ResponseEntity<?> createHistory(@Valid @RequestBody TravelHistoryDto historyDto) {
        travelHistoryService.save(historyDto.toEntity());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getTravelHistoryByUser(String userId) {
        return ResponseEntity.ok().build();
    }
}
