package com.TaskManagement.TaskManagementServer.Config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.slf4j.Logger;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Fetch token from request
        String jwtToken=getTokenFromRequest(request);
        String userName=null;
        //if jwtToken is present and authentication not set
        if(jwtToken!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //validate token
            if(jwtUtils.validateToken(jwtToken)){
                try {
                    userName=jwtUtils.getUsernameFromToken(jwtToken);
                }
                catch (IllegalArgumentException e) {
                    logger.info("Illegal Argument while fetching the username !!");
                    e.printStackTrace();
                } catch (ExpiredJwtException e) {
                    logger.info("Given jwt token is expired !!");
                    e.printStackTrace();
                } catch (MalformedJwtException e) {
                    logger.info("Some changed has done in token !! Invalid Token");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //if we got valid jwtToken and username then set security context
                if(userName!=null){
                    UserDetails userDetails =userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else{
                    logger.info("Invalid user");
                }

            }
            else {
                logger.info("JwtToken Validation fails !!");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        //extract authentication header
        //AUTHORIZATION:Bearer_<JWT_Token>
        String header=request.getHeader(HttpHeaders.AUTHORIZATION);
        //Bearer_<JWT_Token>
        if(header!=null && header.startsWith("Bearer"))
            return header.substring(7);
        else
            return null;
    }
}
