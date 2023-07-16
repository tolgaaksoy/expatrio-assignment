package com.expatrio.usermanagement.init;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.dao.RoleDAO;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.repository.DepartmentRepository;
import com.expatrio.usermanagement.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Profile("!prod") // Activate this configuration for all profiles except "prod"
@Slf4j
@Component
public class FakeDataInitializer {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder encoder;
    private static final String[] DEPARTMENTS = {"IT", "HR", "Finance", "Marketing", "Sales"};

    public FakeDataInitializer(UserRepository userRepository, DepartmentRepository departmentRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.encoder = encoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationStart() {
        Faker faker = new Faker();
        if (departmentRepository.count() == 0) {
            for (String departmentName : DEPARTMENTS) {
                DepartmentDAO departmentDAO = new DepartmentDAO();
                departmentDAO.setName(departmentName);
                departmentRepository.save(departmentDAO);
            }
        }

        if (userRepository.count() == 0) {
            List<DepartmentDAO> departments = departmentRepository.findAll(0, 10);

            for (int i = 0; i < 100; i++) {
                String name = faker.name().fullName();
                UserDAO userDAO = new UserDAO();
                userDAO.setPassword(encoder.encode("password"));
                userDAO.setUsername(name.toLowerCase().replace(" ", "."));
                RoleDAO roleDAO = new RoleDAO();
                if (faker.number().randomNumber() % 2 == 0) {
                    roleDAO.setRoleType("ROLE_USER");
                } else {
                    roleDAO.setRoleType("ROLE_ADMIN");
                }
                userDAO.setRoles(Set.of(roleDAO));
                userDAO.setName(name);
                userDAO.setDepartment(departments.get(faker.number().numberBetween(0, departments.size())));
                userDAO.setSalary(BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000)));
                userRepository.save(userDAO);
            }
        }
    }
}