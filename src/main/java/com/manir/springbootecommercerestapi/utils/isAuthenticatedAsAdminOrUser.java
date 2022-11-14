package com.manir.springbootecommercerestapi.utils;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
public @interface isAuthenticatedAsAdminOrUser {
}
