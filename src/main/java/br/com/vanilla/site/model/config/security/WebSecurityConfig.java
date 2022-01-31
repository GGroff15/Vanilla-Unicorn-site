package br.com.vanilla.site.model.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.impl.FactoryDao;
import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.model.config.security.handler.CustomAccessDeniedHandler;
import br.com.vanilla.site.model.config.security.handler.CustomAuthenticationFailureHandler;
import br.com.vanilla.site.model.config.security.handler.CustomAuthenticationSuccessHandler;
import br.com.vanilla.site.model.config.security.handler.CustomLogoutSuccessHandler;
import br.com.vanilla.site.model.config.security.user_details.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		Dao<Usuario, String> dao = FactoryDao.criarUsuarioDao();
		List<Usuario> listaUsuarios = dao.getAll();

		for (Usuario usuario : listaUsuarios) {
			auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(usuario.getUsername())
					.password(usuario.getSenha()).authorities("ADMIN");
		}
		
		auth.userDetailsService(userDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// @formatter:off
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login") // Pagina de login personalizada
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
		.and()
			.logout()
			.logoutSuccessHandler(logoutSuccessHandler())
			.deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler());
		// @formatter:on

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
}