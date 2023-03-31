package ksp.group3.miraiSugoroku.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler
		implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println(roles);
        if (roles.contains("ROLE_ADMIN")) {
        	response.sendRedirect(request.getContextPath() + "/admin/menu");
        }
        else if(roles.contains("ROLE_SUB_ADMIN")){
        	response.sendRedirect(request.getContextPath() + "/subadmin/menu");
        }
	}


}