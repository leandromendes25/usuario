package com.leandromendes25.usuario.business;

import com.leandromendes25.usuario.business.dto.ViaCepDTO;
import com.leandromendes25.usuario.infrastructure.clients.ViaCepClient;
import com.leandromendes25.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO buscaDadosEndereco(String cep){
        try{
        return client.buscaDadosDeEndereco(processarCep(cep));

        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Erro: ",e);
        }

    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ","").replace("-","");
        if(!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)){
        throw new IllegalArgumentException("O cep contém caracteres inválidos");
        }
        return cepFormatado;
    }
}
