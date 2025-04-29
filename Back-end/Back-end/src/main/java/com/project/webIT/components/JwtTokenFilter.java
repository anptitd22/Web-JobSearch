package com.project.webIT.components;

import com.project.webIT.helper.JwtTokenHelper;
import com.project.webIT.services.CompanyDetailServiceImpl;
import com.project.webIT.services.UserDetailServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}") //annotation bean - not lombok
    private String apiPrefix;

    private final JwtTokenHelper jwtTokenUtil;
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
            log.info(token);
            final String subject = jwtTokenUtil.extractSubject(token);
            final String role = jwtTokenUtil.extractRole(token);

            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = null;

                if ("company".equalsIgnoreCase(authType)) {
                    userDetails = companyDetailService.loadUserByUsername(subject);
                } else {
                    userDetails = userDetailService.loadUserByUsername(subject);
                }
                System.out.println(userDetails);

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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ForgotToken expired");
        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isBypassToken (@NonNull HttpServletRequest request){
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("/%s/actuator/health", apiPrefix), "GET"),

//                Pair.of(String.format("%s/companies",apiPrefix),"GET"),
                Pair.of(String.format("%s/companies/images",apiPrefix),"GET"),
                Pair.of(String.format("%s/jobs/images",apiPrefix), "GET"),
                Pair.of(String.format("%s/roles",apiPrefix), "GET"),
                Pair.of(String.format("%s/jobs/get/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/jobs/recommend", apiPrefix), "POST"),
                Pair.of(String.format("%s/jobs/recommend/user", apiPrefix), "POST"),
                Pair.of(String.format("%s/functions/get", apiPrefix), "GET"),
                Pair.of(String.format("%s/locations/provinces",apiPrefix), "GET"),
                Pair.of(String.format("%s/questions/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/questions", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/auth/social-login", apiPrefix),"GET"),
                Pair.of(String.format("%s/users/auth/social/callback", apiPrefix),"GET"),
                Pair.of(String.format("%s/users/auth/social/**", apiPrefix),"GET"),
                Pair.of(String.format("%s/users/auth/**", apiPrefix),"GET"),
                Pair.of(String.format("%s/auth/**", apiPrefix),"GET"),
                Pair.of(String.format("%s/ws/**", apiPrefix),"GET"),

                Pair.of("/auth/**","GET"),

                Pair.of(String.format("%s/mail/auth/reset-password", apiPrefix),"POST"),
                Pair.of(String.format("%s/mail/auth/forgot-password", apiPrefix),"POST"),
                Pair.of(String.format("%s/industries", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/companies/login", apiPrefix), "POST"),

                Pair.of(String.format("%s/companies/get/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/companies/public/**", apiPrefix), "GET"),

                // Swagger
                Pair.of("/api-docs", "GET"),
                Pair.of("/api-docs/**", "GET"),
                Pair.of("/swagger-resources", "GET"),
                Pair.of("/swagger-resources/**", "GET"),
                Pair.of("/configuration/ui", "GET"),
                Pair.of("/configuration/security", "GET"),
                Pair.of("/swagger-ui/**", "GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET")
        );
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        for (Pair<String, String > bypassToken : bypassTokens){
//            if (request.getServletPath().contains(bypassToken.getLeft()) &&
//                    request.getMethod().contains(bypassToken.getRight())){
//                return true;
//            }
//        for (Pair<String, String> bypassToken : bypassTokens) {
            String tokenPath = bypassToken.getLeft();
            String tokenMethod = bypassToken.getRight();

            if (!requestMethod.equalsIgnoreCase(tokenMethod)) continue;

            if (tokenPath.contains("**")) {
                // Convert wildcard to regex
                String regexPath = tokenPath.replace("**", ".*");
                if (requestPath.matches(regexPath)) {
                    return true;
                }
            } else if (requestPath.equals(tokenPath)) {
                return true;
            }

//            String tokenPath = bypassToken.getLeft(); // Đường dẫn (có thể chứa **)
//            String tokenMethod = bypassToken.getRight(); // GET, POST, etc.
//            if (tokenPath.contains("**")) {
//                // Chuyển thành regex pattern
//                String regexPath = tokenPath.replace("**", ".*");
//                Pattern pattern = Pattern.compile(regexPath);
//                Matcher matcher = pattern.matcher(requestPath);
//                if (matcher.matches() && requestMethod.equals(tokenMethod)) {
//                    return true;
//                }
//            }else if (requestPath.equals(tokenPath) && requestMethod.equals(tokenMethod)) {
//                return true;
//            }
        }
        return false;
    }
}
