package com.leandromendes25.usuario.business.converter;

import com.leandromendes25.usuario.business.dto.EnderecoDTO;
import com.leandromendes25.usuario.business.dto.TelefoneDTO;
import com.leandromendes25.usuario.business.dto.UsuarioDTO;
import com.leandromendes25.usuario.infrastructure.entity.Endereco;
import com.leandromendes25.usuario.infrastructure.entity.Telefone;
import com.leandromendes25.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder().nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail()).senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos())).telefones(paraListaTelefones(usuarioDTO.getTelefones())).build();
    }
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        //passando cada um dos endereços
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder().id(enderecoDTO.getId()).rua(enderecoDTO.getRua()).cidade(enderecoDTO.getCidade())
                .cep(enderecoDTO.getCep()).estado(enderecoDTO.getEstado()).complemento(enderecoDTO.getComplemento()).build();
    }
    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOs){
    return telefoneDTOs.stream().map(this::paraTelefones).toList();
    }
    public Telefone paraTelefones(TelefoneDTO telefoneDTO){
        return Telefone.builder().id(telefoneDTO.getId()).numero(telefoneDTO.getNumero()).ddd(telefoneDTO.getDdd()).build();
    }
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder().nome(usuario.getNome())
                .email(usuario.getEmail()).senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos())).telefones(paraListaTelefonesDTO(usuario.getTelefones())).build();
    }
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco){
        //passando cada um dos endereços
        return endereco.stream().map(this::paraEnderecoDTO).toList();
    }
    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento()).build();
    }
    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefone){
        return telefone.stream().map(this::paraTelefonesDTO).toList();
    }
    public TelefoneDTO paraTelefonesDTO(Telefone telefone){
        return TelefoneDTO.builder().id(telefone.getId()).numero(telefone.getNumero())
                .ddd(telefone.getDdd()).build();
    }
    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder().id(entity.getId()).nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                        .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha()).telefones(entity.getTelefones())
                        .enderecos(entity.getEnderecos()).
                build();
    }
    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .usuario_id(entity.getUsuario_id())
                .build();
    }
    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .usuario_id(entity.getUsuario_id())
                .build();
    }
    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO,Long usuarioId ){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .usuario_id(usuarioId)
                .build();
    }
    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long usuarioId){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .usuario_id(usuarioId)
                .build();
    }
}
