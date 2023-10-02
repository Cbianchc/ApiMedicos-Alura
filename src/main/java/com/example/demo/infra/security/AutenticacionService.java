package com.example.demo.infra.security;

import com.example.demo.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuariosRepository.findByLogin(login);
    }
}
