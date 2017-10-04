package com.antipragas.services;

import com.antipragas.models.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario findByEmail(String email);
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario create(Usuario user);
    Usuario edit(Usuario user);
    void deleteById(Long id);
}
