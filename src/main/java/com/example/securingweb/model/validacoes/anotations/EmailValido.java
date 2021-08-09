package com.example.securingweb.model.validacoes.anotations;

import javax.validation.Payload;

public @interface EmailValido {
	
	String message() default "Invalid email";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
