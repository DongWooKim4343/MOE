package erd.exmaple.erd.example.domain.controller;

import erd.exmaple.erd.example.domain.Record_PhotoBodyEntity;
import erd.exmaple.erd.example.domain.dto.RecordRequestDTO;
import erd.exmaple.erd.example.domain.dto.RecordResponseDTO;
import erd.exmaple.erd.example.domain.service.RecordService.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordBodyController {
    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<RecordResponseDTO.RecordResultDTO> createOrUpdateRecord(@RequestBody RecordRequestDTO.RecordDTO recordDTO) {
        RecordResponseDTO.RecordResultDTO createdBody = recordService.createOrUpdateRecord(recordDTO);
        return ResponseEntity.ok(createdBody);
    }

    @GetMapping("/paged")
    public String getPagedRecords(@PageableDefault(size = 4) Pageable pageable, Model model) {
        Page<Record_PhotoBodyEntity> recordPage = recordService.getPagedRecords(pageable);
        int blockLimit = 3;
        int currentPage = pageable.getPageNumber() + 1; // 페이지 번호를 1부터 시작하도록 조정
        int startPage = (((int) (Math.ceil((double) currentPage / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < recordPage.getTotalPages()) ? startPage + blockLimit - 1 : recordPage.getTotalPages();

        model.addAttribute("recordList", recordPage.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", recordPage.getTotalPages());
        model.addAttribute("totalElements", recordPage.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        return "records"; // Thymeleaf 템플릿 파일명 (records.html)
    }
}
