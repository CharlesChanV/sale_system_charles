package com.dgut.dao;

import com.dgut.dto.UserInfoDto;
import com.dgut.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity,String> {
    List<UserEntity> findAll();
    UserEntity findByUsername(String username);
    UserEntity findByUserId(String userId);
}