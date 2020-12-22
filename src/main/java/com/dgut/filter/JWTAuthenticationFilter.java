package com.dgut.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.dao.UserDao;
import com.dgut.entity.RoleEntity;
import com.dgut.entity.UserEntity;
import com.dgut.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/17
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Component
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
//    private final UserDao userDao;
    private final UserMapper userMapper;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserMapper userMapper) {
        super(authenticationManager);
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println(header);
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                System.out.println(user);
//                UserEntity userEntity = userDao.findById(user).orElseThrow(() -> new EntityNotFoundException("找不到"));
                List<RoleEntity> roleList = userMapper.selectRolesByUserId(user);
                if(roleList.isEmpty()) {
                    throw new EntityNotFoundException("找不到");
                }
                Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                for (RoleEntity i:roleList) {
                    authorities.add(new SimpleGrantedAuthority(i.getName()));
                }
//                authorities.add(new SimpleGrantedAuthority(userEntity.getRoles()));
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
//                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }

}
