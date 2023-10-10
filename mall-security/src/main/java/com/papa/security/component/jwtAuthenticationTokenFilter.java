package com.papa.security.component;


import com.papa.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class jwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(tokenHeader);
        if(token!=null&&token.startsWith(tokenHead)){
            String authToken=token.substring(tokenHead.length()+1);
            String userName=jwtTokenUtil.getUserNameByToken(authToken);
            if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
