package br.com.crudclient.service.mapper;

import br.com.crudclient.model.Cliente;
import br.com.crudclient.service.dto.ClienteDTO;
import br.com.crudclient.service.dto.TelefoneDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClienteMapper implements AbstractMapper<Cliente, ClienteDTO> {

    @Override
    public ClienteDTO entidadeParaDTO(Cliente entidade) {
        return ClienteDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .cpf(entidade.getCpf())
                .emails(entidade.getEmails())
                .telefones(entidade.getTelefones().stream().map(telefone -> TelefoneDTO.builder()
                        .numero(telefone.getNumero()).tipo(telefone.getTipo()).build()).collect(Collectors.toList()))
                .endereco(entidade.getEndereco())
                .build();
    }
}
