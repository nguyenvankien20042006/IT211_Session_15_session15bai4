package com.example.session15bai4.config;

import com.example.session15bai4.entity.Role;
import com.example.session15bai4.entity.User;
import com.example.session15bai4.repository.RoleRepository;
import com.example.session15bai4.repository.UserRepository;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role userRole = findOrCreateRole("USER");
        Role librarianRole = findOrCreateRole("LIBRARIAN");
        Role adminRole = findOrCreateRole("ADMIN");

        createUserIfMissing("user", "123456", Set.of(userRole));
        createUserIfMissing("librarian", "123456", Set.of(userRole, librarianRole));
        createUserIfMissing("admin", "123456", Set.of(userRole, librarianRole, adminRole));
    }

    private Role findOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    private void createUserIfMissing(String username, String rawPassword, Set<Role> roles) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        User user = new User(username, passwordEncoder.encode(rawPassword), roles);
        userRepository.save(user);
    }
}
