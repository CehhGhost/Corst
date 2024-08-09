package spring.crut.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.crut.administration.services.CrutUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CrutUserDetailsService crutUserDetailsService;
    private final JWTFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/sections/create").hasAuthority("CREATE_UPDATE_DELETE_SECTIONS")
                .antMatchers(HttpMethod.DELETE, "/sections/{id}").hasAuthority("CREATE_UPDATE_DELETE_SECTIONS")
                .antMatchers(HttpMethod.PUT, "/sections/{id}").hasAuthority("CREATE_UPDATE_DELETE_SECTIONS")
                .antMatchers(HttpMethod.PATCH, "/sections/{id}/set_num/{num}").hasAuthority("CREATE_UPDATE_DELETE_SECTIONS")

                .antMatchers(HttpMethod.POST, "/articles/create").hasAuthority("CREATE_UPDATE_DELETE_ARTICLES")
                .antMatchers(HttpMethod.DELETE, "/articles/{id}").hasAuthority("CREATE_UPDATE_DELETE_ARTICLES")
                .antMatchers(HttpMethod.PUT, "/articles/{id}").hasAuthority("CREATE_UPDATE_DELETE_ARTICLES")

                .antMatchers(HttpMethod.GET, "/documents").hasAnyAuthority("CREATE_DOCUMENTS", "SEE_READ_ALLDOCUMENTS", "UPDATE_DELETE_ALLDOCUMENTS", "ANNOTATE_ALLDOCUMENTS")
                .antMatchers(HttpMethod.GET, "/documents/{id}").hasAnyAuthority("SEE_READ_ALLDOCUMENTS", "UPDATE_DELETE_ALLDOCUMENTS", "ANNOTATE_ALLDOCUMENTS")
                .antMatchers(HttpMethod.POST, "/documents/create").hasAuthority("CREATE_DOCUMENTS")
                .antMatchers(HttpMethod.PUT, "/documents/update/{id}").hasAuthority("UPDATE_DELETE_ALLDOCUMENTS")
                .antMatchers(HttpMethod.DELETE, "/documents/delete/{id}").hasAuthority("UPDATE_DELETE_ALLDOCUMENTS")
                .antMatchers(HttpMethod.PATCH, "/documents/{id}/set_status/0").hasAnyAuthority("ANNOTATE_ALLDOCUMENTS", "CHECK_ANNOTATEDDOCUMENTS")
                .antMatchers(HttpMethod.PATCH, "/documents/{id}/set_status/1").hasAnyAuthority("ANNOTATE_ALLDOCUMENTS", "CHECK_ANNOTATEDDOCUMENTS")
                .antMatchers(HttpMethod.PATCH, "/documents/{id}/set_status/2").hasAuthority("CHECK_ANNOTATEDDOCUMENTS")

                .antMatchers(HttpMethod.POST, "/info/error_tags/create").hasAuthority("CREATE_UPDATE_DELETE_ERRORTAGS")
                .antMatchers(HttpMethod.DELETE, "/info/error_tags/{id}").hasAuthority("CREATE_UPDATE_DELETE_ERRORTAGS")
                .antMatchers(HttpMethod.PUT, "/info/error_tags/{id}").hasAuthority("CREATE_UPDATE_DELETE_ERRORTAGS")

                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(crutUserDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

