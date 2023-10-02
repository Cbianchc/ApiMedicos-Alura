package com.example.demo.controller;


import com.example.demo.domain.usuarios.DatosAutenticacionUsuario;
import com.example.demo.domain.usuarios.Usuario;
import com.example.demo.infra.security.TokenService;
import com.example.demo.infra.security.datosJWTToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager autenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarusuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        var authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());
        var usuarioAutenticado = autenticationManager.authenticate(authToken);
        var tokenJWT = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new datosJWTToken(tokenJWT));
    }

}
