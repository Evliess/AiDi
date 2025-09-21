package evl.io.config;

import evl.io.constant.ServiceConstants;
import evl.io.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication != null) {
            if (authentication.getPrincipal() == null) return authentication;
            String principal = authentication.getPrincipal().toString();
            String token = authentication.getCredentials().toString();
            String[] arr = token.split("\\.");
            if (arr.length != 2) return null;
            String encoded = arr[0];
            String salt = arr[1];
            logger.info("{} is logged", salt);
            String decoded = RestUtils.decryptWithSalt(encoded, salt);
            if (decoded != null && decoded.equals(salt))
                return new UsernamePasswordAuthenticationToken(principal + ServiceConstants.DOUBLE_COLON + principal, principal, List.of());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}