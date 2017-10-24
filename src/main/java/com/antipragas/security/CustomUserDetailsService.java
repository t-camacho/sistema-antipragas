package com.antipragas.security;

import com.antipragas.models.Usuario;
import com.antipragas.models.enums.Status;
import com.antipragas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);

        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha incorretos"));

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
        }else if(usuario.getStatus() != Status.STATUS_ATIVADA){
            throw new UsernameNotFoundException("Usuário " + username + " não cadastrado");
        }else{
            return new User(username, usuario.getSenha(), getPermissao(usuario));
        }
    }

    private Collection<? extends GrantedAuthority> getPermissao(Usuario usuario) {
        Set<SimpleGrantedAuthority> authority = new HashSet<SimpleGrantedAuthority>();
        authority.add(new SimpleGrantedAuthority(usuario.getNivel().toString().toUpperCase()));
        return authority;
    }


}
