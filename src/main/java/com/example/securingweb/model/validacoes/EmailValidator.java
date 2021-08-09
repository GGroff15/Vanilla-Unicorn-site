package com.example.securingweb.model.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.securingweb.model.validacoes.anotations.EmailValido;

public class EmailValidator implements ConstraintValidator<EmailValido, String> {
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z-0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})S";

	@Override
	public void initialize(EmailValido constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return (validateEmail(email));
	}
	
	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
