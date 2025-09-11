package com.seidelsoft.ERPBackend.auth.service;

import com.seidelsoft.ERPBackend.auth.model.entity.User;
import com.seidelsoft.ERPBackend.auth.repository.UserRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, UserRepository> implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailWithPermissions(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Override
    public boolean validar(User entity, StringBuilder msgValidacao) {
        if (StringUtils.isEmpty(entity.getName())) {
            return false;
        }
        if (StringUtils.isEmpty(entity.getEmail())) {
            return false;
        }
        if (StringUtils.isEmpty(entity.getPassword())) {
            return false;
        }
        return msgValidacao.isEmpty();
    }

    @Override
    public void beforeSave(User item) {
        item.setPassword(passwordEncoder.encode(item.getPassword()));
    }
}
