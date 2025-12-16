package com.example.demo.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
     final JwtService jwtService;
     final UserRepository userRepository;
     
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
           final String authHeader= request.getHeader("Authorization");
           final String jwt;
           final String userName;
           if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
           }
              jwt= authHeader.substring(7);
             // userName= jwtService.extractUsername(jwt); 
              try{
                userName=jwtService.extractUsername(jwt);
                }catch (Exception e){
                    filterChain.doFilter(request, response);
                    return; 
                    // this is to make sure that we proceed with the filter chain even if an exception occurs 
                    // so that spring decides whats to be done or not 
              }
              if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5️⃣ Load user from DB
            UserDetails userDetails = userRepository
                    .findUserByEmail(userName)
                    .orElse(null);

            // 6️⃣ Validate token with user details
            if (userDetails != null && jwtService.isTokenValid(jwt, userDetails)) {

                // 7️⃣ Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 8️⃣ Attach request details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                        //passing request with details like ip address session id etc so that it hepls in audits and logging 
                );

                // 9️⃣ Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
              filterChain.doFilter(request, response);
    }
    // Implementation of JWT Authentication Filter
}
/*
========================================================
JWT AUTHENTICATION FILTER — COMPLETE FLOW EXPLANATION
========================================================

1) This filter runs ON EVERY HTTP REQUEST before the controller.

2) It looks for the Authorization header in the format:
   Authorization: Bearer <JWT_TOKEN>

3) If the header is missing OR does not start with "Bearer ",
   the request is passed to the next filter WITHOUT authentication.
   (This allows public endpoints to work.)

4) If a token exists:
   - The token signature is VERIFIED using the secret key.
   - If the payload or signature is tampered with, parsing fails immediately.
   - No claims are read if the signature is invalid.

5) After successful verification:
   - The username (email) is extracted from the token's "subject".
   - This does NOT hit the database yet.

6) Authentication is performed ONLY if:
   - Username is not null
   - No authentication already exists in SecurityContext
   (Prevents duplicate authentication for the same request.)

7) The user is then loaded from the database using the extracted username:
   - Confirms user still exists
   - Retrieves latest roles/authorities
   - Ensures account is active

8) Token is validated against the loaded user:
   - Username in token must match DB user
   - Token must not be expired

9) If valid:
   - A UsernamePasswordAuthenticationToken is created
   - Credentials are set to null (password is NEVER stored post-login)
   - Authorities (roles) are attached

10) Request-specific details (IP, session, metadata) are attached
    for auditing and security context.

11) The authentication object is stored in SecurityContextHolder.
    From this point forward:
    - Spring considers the user AUTHENTICATED
    - @PreAuthorize and role checks start working
    - Controllers can safely execute

12) filterChain.doFilter(request, response) is ALWAYS called:
    - To continue to the next filter
    - Or to reach the controller
    - Without this call, the request will never complete

SECURITY GUARANTEES:
✔ Stateless authentication (no session stored)
✔ No password used after login
✔ Token tampering is impossible without the secret key
✔ Database is used ONLY to verify user existence & roles
✔ Fully scalable authentication mechanism

========================================================
*/
