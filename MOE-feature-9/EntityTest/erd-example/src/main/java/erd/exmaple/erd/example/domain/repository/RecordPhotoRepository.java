package erd.exmaple.erd.example.domain.repository;

import erd.exmaple.erd.example.domain.Record_PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordPhotoRepository extends JpaRepository<Record_PhotoEntity, Long> {
}
