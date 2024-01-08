package kr.hs.gbsw.tree.auth.provider;

import kr.hs.gbsw.tree.auth.authentication.JwtAuthentication;
import kr.hs.gbsw.tree.user.model.User;
import kr.hs.gbsw.tree.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String credentials = (String) authentication.getCredentials();
        String userId = jwtUtil.getSubject(credentials);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        return new JwtAuthentication(userDetails, credentials, List.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == JwtAuthentication.class;
    }
}