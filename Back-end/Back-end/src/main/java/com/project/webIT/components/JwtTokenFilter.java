package com.project.webIT.filters;

import com.project.webIT.components.JwtTokenUtils;
import com.project.webIT.models.User;
import com.project.webIT.provider.CompanyDetailServiceImpl;
import com.project.webIT.provider.UserDetailServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}") //annotation bean - not lombok
    private String apiPrefix;

    private final JwtTokenUtils jwtTokenUtil;
    private final UserDetailServiceImpl userDetailService;
    private final CompanyDetailServiceImpl companyDetailService;

    @Override
    protected void doFilterInternal (
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            if (isBypassToken(request)){
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            final String authType = request.getHeader("X-Auth-Type");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String subject = jwtTokenUtil.extractSubject(token);
            final String role = jwtTokenUtil.extractRole(token);

            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = null;

                if ("company".equalsIgnoreCase(authType)) {
                    userDetails = companyDetailService.loadUserByUsername(subject);
                } else {
                    userDetails = userDetailService.loadUserByUsername(subject);
                }

                if (jwtTokenUtil.validateToken(token, userDetails)){
                    List<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                    );
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    authorities
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); //enable bypass
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isBypassToken (@NonNull HttpServletRequest request){
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/companies",apiPrefix),"GET"),
                Pair.of(String.format("%s/companies/images",apiPrefix),"GET"),
                Pair.of(String.format("%s/jobs/images",apiPrefix), "GET"),
                Pair.of(String.format("%s/roles",apiPrefix), "GET"),
                Pair.of(String.format("%s/jobs", apiPrefix), "GET"),
                Pair.of(String.format("%s/functions", apiPrefix), "GET"),
                Pair.of(String.format("%s/locations/provinces",apiPrefix), "GET"),
                Pair.of(String.format("%s/questions", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/auth/social-login", apiPrefix),"GET"),
                Pair.of(String.format("%s/users/auth/social/callback", apiPrefix),"GET"),
                Pair.of(String.format("%s/users/auth/social/**", apiPrefix),"GET"),
                Pair.of(String.format("%s/mail/auth/reset-password", apiPrefix),"POST"),
                Pair.of(String.format("%s/mail/auth/forgot-password", apiPrefix),"POST"),
                Pair.of(String.format("%s/industries", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/companies/login", apiPrefix), "POST")
        );
        for (Pair<String, String > bypassToken : bypassTokens){
            if (request.getServletPath().contains(bypassToken.getLeft()) &&
                    request.getMethod().contains(bypassToken.getRight())){
                return true;
            }
        }
        return false;
    }
}
