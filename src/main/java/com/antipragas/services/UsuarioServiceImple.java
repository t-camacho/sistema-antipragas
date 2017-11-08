package com.antipragas.services;

import com.antipragas.models.Usuario;
import com.antipragas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return this.userRepository.findByEmail(email);
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
