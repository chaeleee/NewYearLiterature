package party.of.newyearliterature.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

/**
 * BasicConfiguration
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BasicConfiguration extends WebSecurityConfigurerAdapter{

    private final MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(myUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/user/secure").authenticated()
            .antMatchers("/api/admin").hasRole("admin")
            .anyRequest().permitAll()
            .and()
            .httpBasic()
            .and()
            .headers().frameOptions().disable();
            // .and()
            // .sessionManagement()
            // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}