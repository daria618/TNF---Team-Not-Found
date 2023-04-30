package tnf.back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource; // TODO: 30.04.2023 Заменить на подключение рекомендуемое SB

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/registration").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery(
                "select username, password, active from users where username=?");
        manager.setAuthoritiesByUsernameQuery(
                "select u.username, ur.roles from users u inner join user_role ur on u.id = ur.user_id where u.username=?");
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //TODO Заменить на нормальный энкодер
        return NoOpPasswordEncoder.getInstance();
    }
}