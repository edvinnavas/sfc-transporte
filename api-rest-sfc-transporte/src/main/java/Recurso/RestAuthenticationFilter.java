package Recurso;

import java.io.IOException;
import java.io.Serializable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestAuthenticationFilter implements jakarta.servlet.Filter, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String AUTHENTICATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
            AuthenticationService authenticationService = new AuthenticationService();
            boolean authenticationStatus = authenticationService.authenticate(authCredentials);
            if (authenticationStatus) {
                filter.doFilter(request, response);
            } else {
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
