package br.com.crudclient.security;

import br.com.crudclient.model.Usuario;
import br.com.crudclient.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return UserDetailsCustom.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(getPerfis(usuario))
                .build();
    }

    private Collection<? extends GrantedAuthority> getPerfis(Usuario usuario) {
        return usuario.getPerfis()
                .stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
                .collect(Collectors.toSet());
    }

}
