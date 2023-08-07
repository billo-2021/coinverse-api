package com.coinverse.api.core.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.coinverse.api.common.security.exceptions.*;
import com.coinverse.api.common.security.models.RolePrincipal;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.security.utils.AuthenticationUtils;
import com.coinverse.api.common.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPointErrorProvider authEntryPointErrorProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String token = extractTokenFromRequest(request)
                    .orElseThrow(MissingCredentialsException::new);

            final DecodedJWT decodedJWT = jwtService.decodeToken(token);

            final String username = jwtService.extractUsername(decodedJWT);
            final UserAccount userAccount = (UserAccount) userDetailsService.loadUserByUsername(username);
            final Set<RolePrincipal> authorities = userAccount.getAuthorities();
            AuthenticationUtils.doAuthenticationChecks(userAccount);

            final SecurityContext context = SecurityContextHolder.createEmptyContext();

            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userAccount, "", authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        } catch(ApiAuthenticationException apiAuthEx) {
            authEntryPointErrorProvider.setErrorResponse(
                    AuthenticationUtils.translateApiAuthenticationException(apiAuthEx)
            );

        } catch (Exception ex) {
            authEntryPointErrorProvider.setErrorResponse(AuthenticationUtils.translateException(ex));

        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private Optional<String> extractTokenFromRequest(final HttpServletRequest request) {
        final String authenticationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authenticationHeader) && authenticationHeader.startsWith("Bearer ")) {
            return Optional.of(authenticationHeader.substring(7));
        }

        return Optional.empty();
    }
}
