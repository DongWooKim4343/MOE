package erd.exmaple.erd.example.domain.repository;

import erd.exmaple.erd.example.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import erd.exmaple.erd.example.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //List<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByNickname(String nickname);
}
