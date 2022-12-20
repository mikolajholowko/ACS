package app.application.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers("/main").hasAnyRole("ADMIN", "EMPLOYEE")
                .mvcMatchers("/login").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and().logout()
                .logoutSuccessUrl("/login");
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .mvcMatchers("/main").hasAnyRole("ADMIN", "EMPLOYEE")
//                .mvcMatchers("/login").permitAll()
//                .and()
//                .formLogin().loginPage("/login")
//                .permitAll()
//                .successHandler(loginSuccessHandler)
//                .defaultSuccessUrl("/guest")
//                .and().logout()
//                .logoutSuccessUrl("/login");
//        http.headers().frameOptions().sameOrigin();
//    }
}