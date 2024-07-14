package erd.exmaple.erd.example.domain;

import erd.exmaple.erd.example.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@Table(name = "record_photo_body")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Record_PhotoBodyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    @ColumnDefault("")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_photo_id")
    private Record_PhotoEntity recordPhoto; // 필드 이름을 수정했습니다.
}
