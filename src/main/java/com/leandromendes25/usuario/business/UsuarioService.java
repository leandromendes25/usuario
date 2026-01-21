package com.leandromendes25.usuario.business;

import com.leandromendes25.usuario.business.converter.UsuarioConverter;
import com.leandromendes25.usuario.business.dto.EnderecoDTO;
import com.leandromendes25.usuario.business.dto.TelefoneDTO;
import com.leandromendes25.usuario.business.dto.UsuarioDTO;
import com.leandromendes25.usuario.infrastructure.entity.Endereco;
import com.leandromendes25.usuario.infrastructure.entity.Telefone;
import com.leandromendes25.usuario.infrastructure.entity.Usuario;
import com.leandromendes25.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.leandromendes25.usuario.infrastructure.exceptions.UnathorizedException;
import com.leandromendes25.usuario.infrastructure.repository.EnderecoRepository;
import com.leandromendes25.usuario.infrastructure.repository.TelefoneRepository;
import com.leandromendes25.usuario.infrastructure.repository.UsuarioRepository;
import com.leandromendes25.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final AuthenticationManager authenticationManager;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        verificaEmailExistente(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public String autenticarUsuario(UsuarioDTO usuarioDTO) throws UnathorizedException {
        try{

        Authentication authentication = authenticationManager.
                authenticate(
                        new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
        }catch (BadCredentialsException | UsernameNotFoundException ex){
            throw new UnathorizedException("Usuario ou senha inválidos" + ex.getCause());
        }
        }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try{
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + email)));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado:" + email);
        }
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
    String email = jwtUtil.extractUsername(token.substring(7));
    dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );
    Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));
    Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
    usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
    return usuarioConverter.paraUsuarioDTO(usuario);
    }
    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado: " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }
    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Telefone não encontrado: " + idTelefone));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO,entity);
        return usuarioConverter.paraTelefonesDTO(telefoneRepository.save(telefone));
    }
    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado: " + email));
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }
    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado: " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefonesDTO(telefoneRepository.save(telefone));
    }
}
