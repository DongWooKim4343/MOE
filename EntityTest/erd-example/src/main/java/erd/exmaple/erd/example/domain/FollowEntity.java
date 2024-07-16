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
@Table(name="follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class FollowEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
//애매
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "exhibition_id")
   private ExhibitionEntity exhibition;
//이것도 애매
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "popup_store_id")
   private Popup_StoreEntity popup_store;
}
