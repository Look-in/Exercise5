package by.config.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

/**
 * Web security configuration with dataSource access
 * antMatchers("/login/**") used for redirecting to the default Spring login window.
 *
 * @author Serg Shankunas <shserg2012@gmail.com>
 */
@Configuration
@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from users inner join roles " +
                                "on users.role_id=roles.id where username=?");
    }

    /**
     * Only for debugging uses not encoding pass authentication.
     * Since Spring-5.0.0 changes the default password method to encoding.
     */
    @SuppressWarnings("deprecation")
    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
        http.authorizeRequests()
                .antMatchers("/login/**").access("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_CLIENT', 'ROLE_BOOKMAKER')")
                .antMatchers("/place**").access("hasRole('ROLE_CLIENT')")
                .antMatchers("/modify-**").access("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_BOOKMAKER')")
                .antMatchers("/modify-rate/change-rateResult").access("hasRole('ROLE_ADMINISTRATOR')")
                .antMatchers("/delete**").access("hasRole('ROLE_BOOKMAKER')")
                .and().exceptionHandling().accessDeniedPage("/WEB-INF/jsp/403.jsp")
                .and().logout().logoutSuccessUrl("/view-race")
                .and().formLogin().defaultSuccessUrl("/", false);
    }
}
