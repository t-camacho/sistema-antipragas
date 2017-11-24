package com.antipragas.services;

import com.antipragas.models.Usuario;
import com.antipragas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author Thais Camacho
 */

@Service
@Primary
public class UsuarioServiceImple implements UsuarioService {
    @Autowired
    private UsuarioRepository userRepository;
    @Override
    public Usuario findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public List<Usuario> findAll() {
        return this.userRepository.findAll();
    }
    @Override
    public Usuario findById(Long id) {
        return this.userRepository.findOne(id);
    }
    @Override
    public Usuario create(Usuario usuario) {
        return this.userRepository.save(usuario);
    }
    @Override
    public Usuario edit(Usuario usuario) {
        return this.userRepository.save(usuario);
    }
    @Override
    public void deleteById(Long id) {
        this.userRepository.delete(id);
    }

}
