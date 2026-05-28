package com.example.sistemadereclutamiento.auth.service;


import com.example.sistemadereclutamiento.auth.dto.LoginRequest;
import com.example.sistemadereclutamiento.auth.dto.TokenResponse;
import com.example.sistemadereclutamiento.refreshToken.entity.RefreshToken;
import com.example.sistemadereclutamiento.refreshToken.repository.RefreshTokenRepository;
import com.example.sistemadereclutamiento.refreshToken.service.RefreshTokenService;
import com.example.sistemadereclutamiento.security.jtw.JwtProperties;
import com.example.sistemadereclutamiento.security.jtw.JwtService;
import com.example.sistemadereclutamiento.security.service.CustomUserDetail;
import com.example.sistemadereclutamiento.shared.exeption.BusinessException;
import com.example.sistemadereclutamiento.usuario.entity.Usuario;
import com.example.sistemadereclutamiento.usuario.repository.UsuarioRepositorio;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties props;
    private final RefreshTokenService  refreshTokenService;

    private final UsuarioRepositorio usuarioRepositorio;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;


    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        CustomUserDetail custom = (CustomUserDetail) authentication.getPrincipal();
        Usuario usuario = custom.getUsuario();

        String token = jwtService.generateToken(custom);

        String username = jwtService.extractClaim(token, Claims::getSubject);
        long expiredIn = props.getExpiration() / 1000;

        List<String> roles = jwtService.extractClaim(token,
                claims -> claims.get("roles", List.class));

        List<String> permisos = jwtService.extractClaim(token,
                claims -> claims.get("permisos", List.class));

        String rol = (roles != null && !roles.isEmpty())
                ? roles.get(0).replace("ROLE_", "")
                : null;

        RefreshToken refreshToken = refreshTokenService.crearRefreshToken(usuario);

        return new TokenResponse(token, refreshToken.getToken(), "bearer", expiredIn, username, rol, permisos);
    }
    @Transactional
    public TokenResponse refresh(String token) {


        if (token == null || token.isBlank()) {
            throw new BusinessException("Refresh token es requerido");
        }


        RefreshToken storedToken = refreshTokenService.validarRefreshToken(token);


        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);


        Usuario usuario = storedToken.getUsuario();
        if (!usuario.isActivo()) {
            throw new BusinessException("Usuario desactivado");
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());


        String newAccessToken = jwtService.generateToken(userDetails);


        RefreshToken newRefreshToken = refreshTokenService.crearRefreshToken(usuario);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .toList();

        List<String> permisos = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .toList();

        String rol = (roles != null && !roles.isEmpty())
                ? roles.get(0).replace("ROLE_", "")
                : null;

        log.info("Tokens renovados para usuario: {}", usuario.getEmail());

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("bearer")
                .expiresIn(props.getExpiration() / 1000)
                .username(usuario.getEmail())
                .rol(rol)
                .build();
    }

    public void logout(String refreshToken){
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new BusinessException("token no existe"));
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
