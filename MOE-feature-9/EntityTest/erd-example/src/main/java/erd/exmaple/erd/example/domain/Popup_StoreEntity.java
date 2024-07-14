package erd.exmaple.erd.example.domain;

import erd.exmaple.erd.example.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "popup_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Popup_StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "popupStore", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Record_PageEntity> recordPageEntityList = new ArrayList<>(); // 필드 이름을 수정합니다.
}
