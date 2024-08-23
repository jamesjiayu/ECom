package com.usc.ECom.handler;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
//import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
import com.usc.ECom.security.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
//why no autowired  SecurityUtils, when must autowire sth?
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	//what exception should be here?
        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, "Login successful", null); //SC_OK is code: int 200
    }
}
