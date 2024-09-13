package com.emazon.api_stock.infraestructure.securityconfig;

import com.emazon.api_stock.infraestructure.securityconfig.jwtconfiguration.JwtService;
import com.emazon.api_stock.infraestructure.utils.InfraestructureConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {

        String username = jwtService.extractUsername(jwt);
        String role = jwtService.extractRole(jwt);
        Collection<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(role)
        );

        return new User(username, InfraestructureConstants.COMA, authorities);
    }
}