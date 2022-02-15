package br.com.vanilla.site.config.security.handler;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RefererAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {

	public RefererAuthenticationSuccessHandler() {
		super();
		setUseReferer(true);
	}

}
