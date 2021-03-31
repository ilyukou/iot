package by.grsu.iot.security.config;

import by.grsu.iot.security.jwt.JwtSecurityConfigurer;
import by.grsu.iot.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security Configuration
 *
 * @author Ilyukou Ilya
 */
@PropertySource("classpath:application-security.properties")
@Configuration
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

                .antMatchers("/crud/project").hasRole("USER")
                .antMatchers("/crud/project/**").hasRole("USER")

                .antMatchers("/crud/device").hasRole("USER")
                .antMatchers("/crud/device/**").hasRole("USER")

                .antMatchers("/crud/sensor").hasRole("USER")
                .antMatchers("/crud/sensor/**").hasRole("USER")

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

                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}