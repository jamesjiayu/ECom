package com.usc.ECom.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usc.ECom.http.Response;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendResponse(HttpServletResponse response, int status, String message, Exception exception) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(status);

        // Create response object
        Response responseObj = new Response(exception == null, status, message);
        
        // Write JSON response
        writer.print(mapper.writeValueAsString(responseObj));
        writer.flush();
        writer.close();
    }

	public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
		boolean  isAdm=false;
		for(GrantedAuthority authority: authorities) {
			if(authority.getAuthority().equals("ROLE_ADMIN")) {
				isAdm=true;
			}
		}
		return isAdm;
	}
}
