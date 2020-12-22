package com.dgut.filter;

import com.alibaba.fastjson.JSON;
import com.dgut.param.UserLoginDto;
import com.dgut.service.Impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

//    @Override
//    public void setFilterProcessesUrl(String filterProcessesUrl) {
//        super.setFilterProcessesUrl("/doLogin");
//    }

    /*
     * @Description:接收并解析用户凭证
     * @param req 1
     * @param res 2
     * @return : org.springframework.security.core.Authentication
     * @author : CR
     * @date : 2020/6/17 10:52
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
//        try {
//            UserRegisterDto user = new ObjectMapper()
//                    .readValue(req.getInputStream(), UserRegisterDto.class);
            UserLoginDto user = new UserLoginDto(req.getParameter("username"),req.getParameter("password"));
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * @Description:用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     * @param req 1
     * @param res 2
     * @param chain 3
     * @param auth 4
     * @return : void
     * @author : CR
     * @date : 2020/6/17 10:52
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        System.out.println("successfulAuthentication");
        System.out.println(auth.getPrincipal());
        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        res.addHeader("Authorization", "Bearer " + token);

        Map<String,Object> aut = new HashMap<String,Object>();
        aut.put("Authorization","Bearer " + token);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",0);
        map.put("msg","登录成功");
        map.put("data",aut);
        res.setContentType("application/json;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.write(JSON.toJSONString(map));
    }
    /**
     * 验证【失败】调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returnData="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            returnData="账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            returnData="密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            returnData="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            returnData="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            returnData="账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            returnData="用户不存在";
        }
        // 其他错误
        else{
            returnData="未知异常";
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",401);
        map.put("msg",returnData);
        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");
        // 将反馈塞到HttpServletResponse中返回给前台
//        response.getWriter().write(JSON.toJSONString(returnData));
        response.getWriter().write(JSON.toJSONString(map));
    }



}
