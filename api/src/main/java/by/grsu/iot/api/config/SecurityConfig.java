package by.grsu.iot.api.config;

import by.grsu.iot.api.security.JwtSecurityConfigurer;
import by.grsu.iot.api.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security Configuration
 *
 * @author Ilyukou Ilya
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

                .antMatchers("/auth").permitAll()
                .antMatchers("/auth/**").permitAll()

                .antMatchers("/crud").hasRole("USER")
                .antMatchers("/crud/**").hasRole("USER")

                .antMatchers("/pagination/**").hasRole("USER")
                .antMatchers("/pagination").hasRole("USER")

                .antMatchers("/deviceState/**").permitAll()
                .antMatchers("/deviceState").permitAll()

                .antMatchers("/sensor/value/**").permitAll()
                .antMatchers("/sensor/value").permitAll()

                .antMatchers("/validation").permitAll()
                .antMatchers("/validation/**").permitAll()

                .antMatchers("/properties/**").permitAll()
                .antMatchers("/properties").permitAll()

                .antMatchers("/email/**").permitAll()
                .antMatchers("/email").permitAll()

                .antMatchers("/v2/api-docs",
                        "/swagger-ui",
                        "/swagger-ui/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}