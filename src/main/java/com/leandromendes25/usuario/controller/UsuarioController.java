package com.leandromendes25.usuario.controller;

import com.leandromendes25.usuario.business.UsuarioService;
import com.leandromendes25.usuario.business.ViaCepService;
import com.leandromendes25.usuario.business.dto.EnderecoDTO;
import com.leandromendes25.usuario.business.dto.TelefoneDTO;
import com.leandromendes25.usuario.business.dto.UsuarioDTO;
import com.leandromendes25.usuario.business.dto.ViaCepDTO;
import com.leandromendes25.usuario.infrastructure.config.SecurityConfig;
import com.leandromendes25.usuario.infrastructure.entity.Usuario;
import com.leandromendes25.usuario.infrastructure.exceptions.UnathorizedException;
import com.leandromendes25.usuario.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
@Tag(name = "Tarefas", description = "Cadastro de tarefas")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final ViaCepService viaCepService;

    @PostMapping
    @Operation(summary = "Salvar Usuário", description = "Salva um novo usuario")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
    @Operation(summary = "Logar Usuário", description = "Login de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) throws UnathorizedException {
    return ResponseEntity.ok(usuarioService.autenticarUsuario(usuarioDTO));
    }
    @GetMapping
    @Operation(summary = "Busca dados de Usuários por Email", description = "Buscar dados de úsuario")
    @ApiResponse(responseCode = "200", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }
    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar Usuários por Id", description = "Deleta um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    @Operation(summary = "Atualizar Dados de Usuários", description = "Atualiza dados usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token,dto));
    }
    @PutMapping("/endereco")
    @Operation(summary = "Atualiza endereço de usuário", description = "Atualiza endereço de usuário")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto, @RequestParam("id") Long id){
    return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }
    @PutMapping("/telefone")
    @Operation(summary = "Atualiza telefone de Usuário", description = "Atualiza telefone de usuário")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }
    @PostMapping("/endereco")
    @Operation(summary = "Cria endereço de usuário", description = "Cadastra endereço de usuário")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }
    @PostMapping("/telefone")
    @Operation(summary = "Cadastra telefone", description = "Cadastra telefone de usuário")
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @ApiResponse(responseCode = "401", description = "Credenciais inválida")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
    @GetMapping("/endereco/{cep}")
    @Operation(summary = "Busca endereço por cep", description = "Busca endereço do usuário por cep")
    @ApiResponse(responseCode = "200", description = "Cep buscado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ViaCepDTO> buscarDadosDeCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscaDadosEndereco(cep));
    }
}
