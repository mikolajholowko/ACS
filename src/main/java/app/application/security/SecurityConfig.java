package app.application.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
//                .mvcMatchers("/admin-create-account").hasRole("ADMIN")
//                .mvcMatchers("/admin-available-rooms").hasRole("ADMIN")
//                .mvcMatchers("/admin-account-update").hasRole("ADMIN")
//                .mvcMatchers("/employee-available-rooms").hasAnyRole("ADMIN", "EMPLOYEE")
//                .mvcMatchers("/employee-account-update").hasAnyRole("ADMIN", "EMPLOYEE")
                .mvcMatchers("/admin").hasAnyRole("ADMIN")
                .mvcMatchers("/employee").hasAnyRole("ADMIN", "EMPLOYEE")
                .mvcMatchers("/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler);
//                .and()
////                .logout().logoutSuccessUrl("/logout").invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

}