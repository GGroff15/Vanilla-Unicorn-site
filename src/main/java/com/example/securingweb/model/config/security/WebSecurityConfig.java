package com.example.securingweb.model.config.security;

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

import com.example.securingweb.model.config.security.handler.CustomAccessDeniedHandler;
import com.example.securingweb.model.config.security.handler.CustomAuthenticationFailureHandler;
import com.example.securingweb.model.config.security.handler.CustomAuthenticationSuccessHandler;
import com.example.securingweb.model.config.security.handler.CustomLogoutSuccessHandler;
import com.example.securingweb.model.config.security.user_details.CustomUserDetailsService;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();
		List<UsuarioVO> listaUsuarios = dao.getAll();

		for (UsuarioVO usuario : listaUsuarios) {
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