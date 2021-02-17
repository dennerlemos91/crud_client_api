package br.com.crudclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Endereco {
    private String logradouro;
    private String cidade;
    private String uf;
    private String bairro;
    private String cep;
    private String complemento;
}
