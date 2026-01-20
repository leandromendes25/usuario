package com.leandromendes25.usuario.infrastructure.clients;

import com.leandromendes25.usuario.business.dto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "${viacep.url}")
public interface ViaCepClient {
    @GetMapping("/ws/{cep}/json/")
    ViaCepDTO buscaDadosDeEndereco(@PathVariable("cep") String cep);
}
