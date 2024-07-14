package erd.exmaple.erd.example.domain.repository;

import erd.exmaple.erd.example.domain.Record_PhotoBodyEntity;
import erd.exmaple.erd.example.domain.Record_PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordPhotoBodyRepository extends JpaRepository<Record_PhotoBodyEntity, Long> {
    Optional<Record_PhotoBodyEntity> findByRecordPhoto(Record_PhotoEntity recordPhoto);
}
