package com.example.securingweb.model.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.securingweb.model.validacoes.anotations.EmailValido;

public class EmailValidator implements ConstraintValidator<EmailValido, String> {

	private static final String EMAIL_PATTERN = "^[A-Z0-9+_.-]+@[A-Z0-9.-]+$";

	@Override
	public void initialize(EmailValido constraintAnnotation) {
		throw new UnsupportedOperationException("Não implementado");
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return (validateEmail(email));
	}

	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
