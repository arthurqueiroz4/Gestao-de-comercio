package br.com.JWT;

import br.com.service.impl.MercadoServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private MercadoServiceImpl mercadoService;

    public JwtAuthFilter(JwtService jwtService, MercadoServiceImpl mercadoService){
        this.jwtService = jwtService;
        this.mercadoService = mercadoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization!=null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            if(jwtService.tokenIsValid(token)){
                String loginMercado = jwtService.obterLoginUsuario(token);
                UserDetails mercado = mercadoService.loadUserByUsername(loginMercado);
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(mercado, null, mercado.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
            }

        }

        filterChain.doFilter(request, response );
    }
}
