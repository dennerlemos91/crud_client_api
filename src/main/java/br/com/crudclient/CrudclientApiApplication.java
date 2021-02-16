package br.com.crudclient;

import br.com.crudclient.model.Usuario;
import br.com.crudclient.model.enums.Perfil;
import br.com.crudclient.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class CrudclientApiApplication implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(CrudclientApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Set<Integer> perfilAdmin = new HashSet<>();
		perfilAdmin.add(Perfil.ADMIN.getCod());

		final var usuarioAdmin = Usuario.builder()
				.username("admin")
				.password("$2a$10$VlnSOCD3Pe5t0H0Q14b2ueHQhwNsJfovd0A9SYQuhQa/x2E21TO5.")
				.perfis(perfilAdmin)
				.build();

		Set<Integer> perfilComum = new HashSet<>();
		perfilAdmin.add(Perfil.ADMIN.getCod());

		final var usuarioComum = Usuario.builder()
				.username("comum")
				.password("$2a$10$VlnSOCD3Pe5t0H0Q14b2ueHQhwNsJfovd0A9SYQuhQa/x2E21TO5.")
				.perfis(perfilComum)
				.build();

		usuarioRepository.saveAll(Arrays.asList(usuarioAdmin, usuarioComum));
	}
}
