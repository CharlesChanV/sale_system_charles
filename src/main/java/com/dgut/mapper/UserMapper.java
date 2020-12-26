package com.dgut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.UserEntity;
import com.dgut.vo.UserInfoVO;
import com.dgut.vo.UserRoleInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.hibernate.annotations.CollectionId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    public UserRoleInfoVO getUserWithRoleByUserId(String user_id);
    public List<RoleEntity> selectRolesByUserId(String user_id);
    @Transactional
    IPage<UserInfoVO> selectPageVo(IPage<?> page, String username);
}
