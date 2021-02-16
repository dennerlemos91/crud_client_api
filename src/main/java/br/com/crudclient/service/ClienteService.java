package br.com.crudclient.service;

import br.com.crudclient.model.Cliente;
import br.com.crudclient.model.Endereco;
import br.com.crudclient.model.Telefone;
import br.com.crudclient.repository.ClienteRepository;
import br.com.crudclient.service.dto.ClienteDTO;
import br.com.crudclient.service.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Cliente findById(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + idCliente));
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }


    @Transactional
    public Cliente create(ClienteDTO dto) {
        dto.setId(null);
        final Cliente obj = Cliente.builder()
                .nome(dto.getNome())
                .emails(dto.getEmails())
                .cpf(dto.getCpf())
                .emails(dto.getEmails())
                .endereco(
                    Endereco.builder()
                            .logradouro(dto.getLogradouro())
                            .bairro(dto.getBairro())
                            .uf(dto.getUf())
                            .cep(dto.getCep())
                            .cidade(dto.getCidade())
                            .build()
                )
                .build();
        obj.setTelefones(
                dto.getTelefones().stream().map(telefoneDTO -> Telefone.builder()
                        .numero(telefoneDTO.getNumero())
                        .tipo(telefoneDTO.getTipo())
                        .cliente(obj)
                        .build())
                        .collect(Collectors.toList())
        );
        return clienteRepository.saveAndFlush(obj);
    }

    @Transactional
    public void update(Integer idCliente, ClienteDTO dto) {
        final Cliente cliente = findById(idCliente);
        cliente.getEmails().clear();

        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefones(
                dto.getTelefones().stream().map(telefoneDto -> Telefone.builder()
                        .id(telefoneDto.getId())
                        .numero(telefoneDto.getNumero())
                        .tipo(telefoneDto.getTipo())
                        .cliente(cliente)
                        .build())
                        .collect(Collectors.toList()
        ));
        cliente.setEmails(dto.getEmails());

        final var endereco = Endereco.builder()
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .uf(dto.getUf())
                .bairro(dto.getBairro())
                .logradouro(dto.getLogradouro())
                .build();


        cliente.setEndereco(endereco);
        clienteRepository.saveAndFlush(cliente);
    }

    @Transactional
    public void delete(Integer idCliente) {
        final Cliente cliente = findById(idCliente);
        clienteRepository.delete(cliente);
    }
}

