package erd.exmaple.erd.example.domain.service.RecordService;

import erd.exmaple.erd.example.domain.Record_PhotoBodyEntity;
import erd.exmaple.erd.example.domain.Record_PhotoEntity;
import erd.exmaple.erd.example.domain.converter.RecordConverter;
import erd.exmaple.erd.example.domain.dto.RecordRequestDTO;
import erd.exmaple.erd.example.domain.dto.RecordResponseDTO;
import erd.exmaple.erd.example.domain.repository.RecordPhotoBodyRepository;
import erd.exmaple.erd.example.domain.repository.RecordPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordServiceImpl implements RecordService {
    private final RecordPhotoBodyRepository recordPhotoBodyRepository;
    private final RecordPhotoRepository recordPhotoRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RecordResponseDTO.RecordResultDTO createOrUpdateRecord(RecordRequestDTO.RecordDTO request) {
        Record_PhotoEntity recordPhoto = recordPhotoRepository.findById(request.getRecordPhotoId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid recordPhotoId"));

        // record_photo_id로 기존 데이터 조회
        Optional<Record_PhotoBodyEntity> optionalRecordPhotoBody = recordPhotoBodyRepository.findByRecordPhoto(recordPhoto);

        Record_PhotoBodyEntity recordPhotoBody;
        if (optionalRecordPhotoBody.isPresent()) {
            // 기존 데이터가 존재하면 업데이트
            recordPhotoBody = optionalRecordPhotoBody.get();
            recordPhotoBody.setBody(request.getBody());
        } else {
            // 기존 데이터가 존재하지 않으면 새로운 데이터를 생성
            recordPhotoBody = RecordConverter.toRecord_PhotoBodyEntity(request, recordPhoto);
        }

        Record_PhotoBodyEntity savedBody = recordPhotoBodyRepository.save(recordPhotoBody);
        return RecordConverter.toRecordResultDTO(savedBody);
    }

    // 페이징된 데이터를 반환하는 메서드 추가
    public Page<Record_PhotoBodyEntity> getPagedRecords(Pageable pageable) {
        return recordPhotoBodyRepository.findAll(pageable);
    }
}
