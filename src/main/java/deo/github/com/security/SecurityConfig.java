package deo.github.com.security;

import deo.github.com.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private static class CrmInMemoryUserDetailsManager extends InMemoryUserDetailsManager {
        public CrmInMemoryUserDetailsManager() {
            createUser(new User("1",
                    "{noop}1",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/images/**").permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class, "/logout");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new CrmInMemoryUserDetailsManager();
    }
}
