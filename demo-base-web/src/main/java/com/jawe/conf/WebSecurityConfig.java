package com.jawe.conf;

import com.jawe.conf.jwt.JwtAuthenticationTokenFilter;
import com.jawe.system.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private DataSource datasource;
//    @Autowired
//    private MyLogoutHandler myLogoutHandler;
//
//    @Autowired
//    private MyLogoutSuccessHandler myLogoutSuccessHandler;
//
//    @Autowired
//    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
//
//    @Autowired
//    private MyAccessDeniedHandler myAccessDeniedHandler;
//
//    @Autowired
//    private MyExpiredSessionStrategy myExpiredSessionStrategy;
//


    //????????????????????????
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //???????????????????????????????????????
        auth.userDetailsService(userDetailsService)
                // ?????????????????????????????????hash???????????????
                .passwordEncoder(passwordEncoder);
    }
    String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/doc.html").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/v2/**").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                ;

        //???1???????????????????????????cors ??????????????????,???Spring security ????????????preflight request???cors ???????????????
        http.cors();
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
//        http.csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringAntMatchers("/authentication");
        http.csrf().disable();

//
//        //???2?????????Security??????????????????HttpSession??????????????????HttpSession?????????SecurityContext
//        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().headers().cacheControl();

        //???3????????????????????????
        http.authorizeRequests()
                .antMatchers("/login.html","/api/**","/api/common/**").permitAll()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers("/api/auth/login", "/api/auth/refreshToken","/doc.html").permitAll()
                .anyRequest().access("@rbacService.hasPermission(request,authentication)");


        //???4????????????token?????????????????? UsernamePasswordAuthenticationFilter ???????????? JwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


//        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").anonymous()
//                .and() .csrf().disable();

//        //???5??????????????????????????????????????????????????????
//        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(myAccessDeniedHandler);
//
        //???6????????????,????????????????????????JSON??????????????????????????????????????????????????????????????????
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        //????????????????????????????????????????????????????????????????????????,????????????(??????????????????)????????????
//        http.sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .expiredSessionStrategy(myExpiredSessionStrategy);
//
//        //???8????????????
//        http.logout().addLogoutHandler(myLogoutHandler).logoutSuccessHandler(myLogoutSuccessHandler).deleteCookies("JSESSIONID");
    }


    //JWT????????????
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000"));
        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE","PATCH"));
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(datasource);

        return tokenRepository;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/*.html",
                "/**/*.css",
                "/**/*.js");
    }
}
