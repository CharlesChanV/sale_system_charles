package com.dgut.config;

import com.dgut.dao.UserDao;
import com.dgut.entity.UserEntity;
import com.dgut.filter.JWTAuthenticationFilter;
import com.dgut.filter.JWTLoginFilter;
import com.dgut.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/user1/**").hasRole("vip1")
//                .antMatchers("/user2/**").hasRole("vip2")
//                .antMatchers("/user3/**").hasRole("vip3");
//
//        http.formLogin();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("chr").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
//                .and()
//                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
//    }

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

//    @Resource
//    UserServiceImpl userServiceImpl;

    @Resource
    UserDao userDao;
    @Resource
    UserMapper userMapper;

    private UserEntity getUser(String id){
//        return userDao.findById(id).orElseThrow(()-> new RuntimeException("找不到这个用户:"+id));
        return userMapper.selectById(id);
    }





    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
//                // Swagger所需放行规则
//                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
//                .antMatchers(HttpMethod.GET, "/csrf").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .addFilter(new JWTLoginFilter(authenticationManager()))
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()));

        http.cors()
                .and()
                // 跨域伪造请求限制无效
                .csrf().disable()
                .authenticationProvider(authenticationProvider())
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/common/register").permitAll()
                .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/admin/signup").permitAll()
                // Swagger所需放行规则
                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/csrf").permitAll()
                .anyRequest().authenticated() //必须授权才能范围
                .and()
                // 添加JWT登录拦截器
                .addFilter(new JWTLoginFilter(authenticationManager()))
                // 添加JWT鉴权拦截器
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),userMapper))
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security永不创建HttpSession 不使用HttpSession来获取SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling()
                // 匿名用户访问无权限资源时的异常
                .authenticationEntryPoint((request,response,authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",999);
                    map.put("msg","未登录");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                //没有权限，返回json
                .accessDeniedHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",403);
                    map.put("msg", "权限不足");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                //退出成功，返回json
                .logoutSuccessHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",0);
                    map.put("msg","退出成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                });

//        http.cors().and().csrf().disable()
//                .authenticationProvider(authenticationProvider())
//                .httpBasic()
//                //未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
//                .authenticationEntryPoint((request,response,authException) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    PrintWriter out = response.getWriter();
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("code",999);
//                    map.put("msg","未登录");
//                    out.write(objectMapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
//
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
//                .antMatchers(HttpMethod.POST, "/admin/signup").permitAll()
//                // Swagger所需放行规则
//                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
//                .antMatchers(HttpMethod.GET, "/csrf").permitAll()
//                .anyRequest().authenticated() //必须授权才能范围
//                .and()
//                .formLogin() //使用自带的登录
//                .permitAll()
//                //登录失败，返回json
//                .failureHandler((request,response,ex) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    PrintWriter out = response.getWriter();
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("code",401);
//                    if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
//                        map.put("msg","用户名或密码错误");
//                    } else if (ex instanceof DisabledException) {
//                        map.put("msg","账户被禁用");
//                    } else {
//                        map.put("msg","登录失败!");
//                    }
//                    out.write(objectMapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
//                //登录成功，返回json
//                .successHandler((request,response,authentication) -> {
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("code",0);
//                    map.put("msg","登录成功");
//                    UserEntity userEntity = this.getUser(authentication.getName());
//                    UserInfoDto userInfoVO = new UserInfoDto(userEntity);
//                    map.put("data",userInfoVO);
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    out.write(objectMapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
//                .and()
//                .exceptionHandling()
//                //没有权限，返回json
//                .accessDeniedHandler((request,response,ex) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    PrintWriter out = response.getWriter();
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("code",403);
//                    map.put("msg", "权限不足");
//                    out.write(objectMapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
//                .and()
//                .logout()
//                //退出成功，返回json
//                .logoutSuccessHandler((request,response,authentication) -> {
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("code",0);
//                    map.put("msg","退出成功");
//                    map.put("data",authentication);
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    out.write(objectMapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
//                .permitAll()
//                .and()
//                .addFilter(new JWTLoginFilter(authenticationManager()))
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()));


        //开启跨域访问
//        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
//        http.csrf().disable();

    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();
    }



}
