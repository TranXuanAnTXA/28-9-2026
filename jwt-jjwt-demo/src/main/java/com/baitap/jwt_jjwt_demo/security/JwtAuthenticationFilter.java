package com.baitap.jwt_jjwt_demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.baitap.jwt_jjwt_demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    // Sử dụng interface UserDetailsService chung của Spring Security thay vì CustomUserDetailsService cụ thể để linh hoạt hơn
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lấy và bóc tách token trực tiếp bằng hàm có sẵn trong JwtService
        String authHeader = request.getHeader("Authorization");
        String token = jwtService.extractTokenFromHeader(authHeader);

        // 2. Nếu token không tồn tại hoặc rỗng thì cho qua filter này luôn
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. Lấy username từ token
            String username = jwtService.extractUsername(token);

            // 4. Kiểm tra nếu username hợp lệ và chưa được xác thực trong SecurityContext
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Kiểm tra tính hợp lệ của token (chữ ký đúng và chưa hết hạn)
                if (jwtService.isValidToken(token)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    // 6. Set thông tin xác thực vào SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            // Token lỗi, hết hạn hoặc sai chữ ký thì bỏ qua, hệ thống sẽ tự xử lý ở tầng bảo mật tiếp theo
        }

        filterChain.doFilter(request, response);
    }

}