package lbs.lbs.config;

import lbs.lbs.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig  {

    private final PrincipalOauth2UserService principalOauth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // 사이트 위변조 요청 방지
                .authorizeHttpRequests((authorizeRequests) -> { // 특정 URL에 대한 권한 설정.
                    authorizeRequests.requestMatchers("/user/**").authenticated();
                    authorizeRequests.requestMatchers("/admin/**")
                            .hasRole("ADMIN"); // ROLE_은 붙이면 안 된다. hasRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
                    authorizeRequests.anyRequest().permitAll();
                })

                .formLogin((formLogin) -> {
                    formLogin
                            .loginPage("/login") // 권한이 필요한 요청은 해당 url로 리다이렉트
                            .usernameParameter("userId") // PrincipalDetailsService에서 userName 대신 userId를 받도록 수정.
                            .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 해준다.
                            .defaultSuccessUrl("/"); //로그인 성공시 /주소로 이동

                })
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(principalOauth2UserService))
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/", true))

                .build();
    }
}


