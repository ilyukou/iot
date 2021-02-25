package by.grsu.iot.service.config;

import by.grsu.iot.service.security.jwt.JwtSecurityConfigurer;
import by.grsu.iot.service.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
@PropertySource("classpath:application-security.properties")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS

                .antMatchers("/auth/**").permitAll()

                .antMatchers("/device").hasRole("USER")
                .antMatchers("/device/**").hasRole("USER")

                .antMatchers("/deviceState/**").permitAll()
                .antMatchers("/deviceState").permitAll()

                .antMatchers("/project").hasRole("USER")
                .antMatchers("/project/**").hasRole("USER")

                .antMatchers("/validation").permitAll()
                .antMatchers("/validation/**").permitAll()

                .antMatchers("/validation").permitAll()
                .antMatchers("/const/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }


}