package erd.exmaple.erd.example.domain.converter;

import erd.exmaple.erd.example.domain.Record_PhotoBodyEntity;
import erd.exmaple.erd.example.domain.Record_PhotoEntity;
import erd.exmaple.erd.example.domain.dto.RecordRequestDTO;
import erd.exmaple.erd.example.domain.dto.RecordResponseDTO;

public class RecordConverter {

    public static RecordResponseDTO.RecordResultDTO toRecordResultDTO(Record_PhotoBodyEntity recordPhotoBody) {
        return RecordResponseDTO.RecordResultDTO.builder()
                .record_photoBodyId(recordPhotoBody.getId())
                .createdAt(recordPhotoBody.getCreated_at())
                .build();
    }

    public static Record_PhotoBodyEntity toRecord_PhotoBodyEntity(RecordRequestDTO.RecordDTO request, Record_PhotoEntity recordPhoto) {
        return Record_PhotoBodyEntity.builder()
                .body(request.getBody())
                .recordPhoto(recordPhoto) // 필드 이름을 반영하여 수정합니다.
                .build();
    }
}
