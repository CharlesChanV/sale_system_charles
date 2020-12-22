package com.dgut.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.UserEntity;
import com.dgut.mapper.UserMapper;
import com.dgut.service.UserService;
import com.dgut.dto.UserInfoDto;
import com.dgut.utils.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
//
////    @Resource
////    private UserServiceImpl userService;
//    @Override
    public UserEntity findByUsername(String username) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }
    public List<RoleEntity> selectRolesByUserId(String user_id) {
        return userMapper.selectRolesByUserId(user_id);
    }
//    @Override
//    public UserEntity save(UserEntity user) {
//        return userDao.save(user);
//    }
//    @Override
//    public void updateUserInfo(UserInfoDto updateUser, String user_id) {
//        UserInfoDto userInfoVO = new UserInfoDto(this.getUser(user_id));
//        updateUser.setId(userInfoVO.getId());
//        userDao.update(updateUser);
//    }
//    @Override
//    public UserEntity changeUserInfo(UserInfoDto userInfoDto) {
//        UserEntity userEntity = this.getUser(userInfoDto.getId());
////        userEntity.setEmail(userInfoDto.getEmail());
////        userEntity.setPhone(userInfoDto.getPhone());
////        userEntity.setAddress(userInfoDto.getAddress());
////        userEntity.setRoles(userInfoDto.getRoles());
////        userEntity.setCanPublish(userInfoDto.getCanPublish());
//        UpdateUtil.copyNullProperties(userInfoDto,userEntity);
//        return userDao.save(userEntity);
//    }
//
//    @Override
//    public void deleteUser(String user_id) {
////        UserEntity userEntity = new UserEntity();
////        userEntity.setId(user_id);
////        userDao.delete(userEntity);
//        userDao.deleteById(user_id);
//    }
//
//    public UserEntity getUser(String id){
//        return userDao.findById(id).orElseThrow(()-> new RuntimeException("找不到这个用户:"+id));
//    }


}
