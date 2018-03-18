package com.dxy.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security配置类
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "dxy";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }//使用BCrypt加密

    //以密文的形式加密密码，防止数据库被攻破，丢失密码
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);//设置加密码方式
        return authenticationProvider;
    }

    /**
     * 自定义配置类
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()//css,js,fonts,index目录下文件都可以访问
                .antMatchers("/h2-console/**").permitAll()//都可以访问
                .antMatchers("/admins/**").hasRole("ADMIN")//需要相应的角色才能访问
                .and()
                .formLogin()                //基于表单登录验证
                .loginPage("/login").failureUrl("/login-error") //自定义登录界面
                .and().rememberMe().key(KEY)//启用remember me
                .and().exceptionHandling().accessDeniedPage("/403");//处理异常，拒绝访问，重定向至403页面
        http.csrf().ignoringAntMatchers("/h2-consoles/**");//禁用H2控制台的CSRF防护
        http.headers().frameOptions().sameOrigin();//允许来自同一来源的H2控制台的请求
    }

    /***
     * 认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}
