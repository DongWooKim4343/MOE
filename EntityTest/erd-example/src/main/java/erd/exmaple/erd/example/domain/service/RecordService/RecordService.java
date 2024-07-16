package erd.exmaple.erd.example.domain.service.RecordService;

import erd.exmaple.erd.example.domain.Record_PhotoBodyEntity;
import erd.exmaple.erd.example.domain.dto.RecordRequestDTO;
import erd.exmaple.erd.example.domain.dto.RecordResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {
    RecordResponseDTO.RecordResultDTO createOrUpdateRecord(RecordRequestDTO.RecordDTO request);
    Page<Record_PhotoBodyEntity> getPagedRecords(Pageable pageable); // 페이징된 데이터를 반환하는 메서드 추가
}
