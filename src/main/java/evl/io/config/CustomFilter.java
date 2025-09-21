package evl.io.config;

import evl.io.constant.ServiceConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomFilter extends OncePerRequestFilter {
    public static final String PUBLIC_PATH_PREFIX = "/public/";
    public static final String X_TOKEN = "X-token";
    public static final String X_OPENID = "X-Openid";

    private final AuthenticationManager authenticationManager;

    public CustomFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith(ServiceConstants.PUBLIC_PATH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(ServiceConstants.X_TOKEN);
        String openid = request.getHeader(ServiceConstants.X_OPENID);
        Authentication authentication = new UsernamePasswordAuthenticationToken(openid, token);
        Authentication verifiedAuth = this.authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(verifiedAuth);
        filterChain.doFilter(request, response);
    }
}
