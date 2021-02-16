package br.com.crudclient.service.dto;

import br.com.crudclient.model.enums.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private Integer id;
    private TipoTelefone tipo;
    private String numero;
}
