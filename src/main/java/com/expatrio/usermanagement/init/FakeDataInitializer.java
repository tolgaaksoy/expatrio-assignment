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
        addExpatrioUser();
        log.info("Fake data initialization started");
        Faker faker = new Faker();
        if (departmentRepository.count() == 0) {
            for (String departmentName : DEPARTMENTS) {
                DepartmentDAO departmentDAO = new DepartmentDAO();
                departmentDAO.setName(departmentName);
                departmentRepository.save(departmentDAO);
            }
        }

        if (userRepository.count() == 0) {

            UserDAO admin = new UserDAO();
            admin.setName("expatrio");
            admin.setUsername("expatrio");
            admin.setPassword(encoder.encode("expatrio"));
            RoleDAO adminRole = new RoleDAO();
            adminRole.setRoleType("ROLE_ADMIN");
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);

            List<DepartmentDAO> departments = departmentRepository.findAll(0, 10);

            for (int i = 0; i < 100; i++) {
                String name = faker.name().fullName();
                String username = name.toLowerCase()
                        .replace(" ", ".")
                        .replace("'", "")
                        .replace("..", ".");
                if (username.endsWith(".")) {
                    username = username.substring(0, username.length() - 1);
                }
                UserDAO userDAO = new UserDAO();
                userDAO.setPassword(encoder.encode("password"));
                userDAO.setUsername(username);
                RoleDAO roleDAO = new RoleDAO();
                if (faker.number().randomNumber() % 2 == 0) {
                    roleDAO.setRoleType("ROLE_USER");
                } else {
                    roleDAO.setRoleType("ROLE_ADMIN");
                }
                userDAO.setRoles(Set.of(roleDAO));
                userDAO.setName(name);
                userDAO.setDepartment(departments.get(faker.number().numberBetween(0, departments.size())));
                userDAO.setSalary(BigDecimal.valueOf(faker.number().numberBetween(300, 1000) * 100L));
                userRepository.save(userDAO);
            }
        }
        log.info("Fake data initialization finished");
    }

    private void addExpatrioUser() {
        String expatrio = "expatrio";
        if (!userRepository.existsByUsername(expatrio)) {

            UserDAO admin = new UserDAO();
            admin.setName(expatrio);
            admin.setUsername(expatrio);
            admin.setPassword(encoder.encode(expatrio));
            RoleDAO adminRole = new RoleDAO();
            adminRole.setRoleType("ROLE_ADMIN");
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            log.info("Expatrio user added");
            log.info("Username: expatrio");
            log.info("Password: expatrio");
        }
    }
}