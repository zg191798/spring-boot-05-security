package com.atguigu.springboot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhangge
 * @date 2019/1/21 - 11:30
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(web);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        // 开启自动配置的登陆功能,如果没有登陆,就会来到登陆页
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");
        //1./login来到登陆页
        //2. 重定向到/login?error表示登陆失败
        //3. 更多详细规则
        //4.一旦定制loginPage:那么loginPage的Post请求就是登陆


        // 开启自动配置的注销功能
        http.logout().logoutSuccessUrl("/"); // 注销成功以后来到首页
        //1.访问 /logout 表示用户注销,清空session
        //2. 注销成功会返回 /login?logout页面

        // 开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");

    }

    // 定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
        //可以设置内存指定的登录的账号密码,指定角色
        //不加.passwordEncoder(new MyPasswordEncoder())
        //就不是以明文的方式进行匹配，会报错
       /* auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");*/

        //.passwordEncoder(new MyPasswordEncoder())。
        //这样，页面提交时候，密码以明文的方式进行匹配。
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .passwordEncoder(new MyPasswordEncoder()).withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .passwordEncoder(new MyPasswordEncoder()).withUser("wangwu").password("123456").roles("VIP1","VIP3");
    }
}
