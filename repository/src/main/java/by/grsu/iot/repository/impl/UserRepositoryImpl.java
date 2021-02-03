package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.UserJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

//    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final RoleRepository roleRepository;
    private final TimeUtil timeUtil;
    private final EmailRepository emailRepository;

    public UserRepositoryImpl(
//            PasswordEncoder passwordEncoder,
            UserJpaRepository userJpaRepository,
            RoleRepository roleRepository, TimeUtil timeUtil, EmailRepository emailRepository) {
//        this.passwordEncoder = passwordEncoder;
        this.userJpaRepository = userJpaRepository;
        this.roleRepository = roleRepository;
        this.timeUtil = timeUtil;
        this.emailRepository = emailRepository;
    }

    @Override
    public User create(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Email email = emailRepository.create(user.getEmail()); // Create Email entity

        user.setEmail(email); // set email with id
        user.setRoles(Arrays.asList(roleRepository.getRoleOrCreate(RoleType.User)));
        user.setEmail(EntityFactory.createEmail(user.getEmail().getAddress()));

        user = userJpaRepository.save(user); // save user with email

        email.setUser(user); // insert user into email entity
        emailRepository.update(email); // save updates


        return user;
    }

    @Override
    public User getById(Long id) {
        return userJpaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean disableUserByUserId(Long userId) {
        User user = getById(userId);

        if (user == null) {
            return false;
        }

        user.setStatus(Status.DISABLED);

        update(user);

        return true;
    }

    @Override
    public boolean isExist(Long id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public User update(User user) {
        user.setUpdated(timeUtil.getCurrentDate());
        return userJpaRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userJpaRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return userJpaRepository.findByUsername(username).isPresent();
    }

    @Override
    public RoleType getUserRole(String username) {
        return userJpaRepository.getUserRoleType(username);
    }

}
