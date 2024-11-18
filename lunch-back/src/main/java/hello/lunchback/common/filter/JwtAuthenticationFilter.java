package hello.lunchback.common.filter;

import hello.lunchback.login.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationFilter : doFilterInternal : Start");
        try {
            String token = parseBearToken(request);
            if (token == null){
                filterChain.doFilter(request,response);
                return;
            }
            Map<String, Object> validate = jwtProvider.validate(token);
            List<String> roles = (List<String>) validate.get("roles");
            List<SimpleGrantedAuthority> list = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .toList();
            UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(validate.get("email"), null, list);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }catch (Exception e){
            log.info("JwtAuthenticationFilter : doFilterInternal : error");
            e.printStackTrace();
        }
        log.info("JwtAuthenticationFilter : doFilterInternal : complete");
        filterChain.doFilter(request,response);

    }

    private String parseBearToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization){
            return null;
        }
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer){
            return null;
        }
        String token = authorization.substring(7);
        return token;

    }
}
