package br.com.crudclient.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private List<TelefoneDTO> telefones;
    private Set<String> emails;

    private String logradouro;
    private String cidade;
    private String uf;
    private String bairro;
    private String cep;
}
