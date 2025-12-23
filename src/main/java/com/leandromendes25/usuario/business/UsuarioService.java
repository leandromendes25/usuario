package com.leandromendes25.usuario.business;

import com.leandromendes25.usuario.business.converter.UsuarioConverter;
import com.leandromendes25.usuario.business.dto.UsuarioDTO;
import com.leandromendes25.usuario.infrastructure.entity.Usuario;
import com.leandromendes25.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
