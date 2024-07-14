package erd.exmaple.erd.example.domain;



import erd.exmaple.erd.example.domain.common.BaseEntity;
import erd.exmaple.erd.example.domain.enums.Ad;
import erd.exmaple.erd.example.domain.enums.Marketing;
import erd.exmaple.erd.example.domain.enums.Provider;
import erd.exmaple.erd.example.domain.enums.LoginStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*  //회원가입시 입력해야할 아이디  (DB 키가 아님)
@Column(nullable = false,length = 20)
private String user_id;*/

    @Column(nullable = false,length = 20)
    private String password;


    @Column(name = "phone_number", nullable = false,length = 11)
    private String phoneNumber; //카멜케이스로 수정

    @Column(nullable = false,length = 100)
    private String nickname; //닉네임을 아이디로 사용

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)")
    private Provider provider;

    @Column
    private String provider_id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private LoginStatus status;

    @Column
    private LocalDate inactive_date;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'INACTIVE'")
    private Ad ad;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'INACTIVE'")
    private Marketing marketing;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Record_PageEntity> Record_PageEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<FollowEntity> FollowEntityList = new ArrayList<>();
}