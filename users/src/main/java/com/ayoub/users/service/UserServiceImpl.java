package com.ayoub.users.service;
import com.ayoub.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ayoub.users.entities.Role;
import com.ayoub.users.entities.User;
import com.ayoub.users.repos.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRep;
    @Autowired
    RoleRepository roleRep;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User saveUser(User user) {
        List<Role>roles=new ArrayList<>();
        Role r=roleRep.findByRole("USER");
        roles.add(r);
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRep.save(user);
    }
    @Override
    public void deleteUser(long id) {
        userRep.deleteByUserId(id);
    }

    @Override
    public User addRoleToUser(long id, Role r) {
        User usr = userRep.findUserById(id);
        List<Role> roles = usr.getRoles();
        roles.add(r);
        usr.setRoles(roles);
        return userRep.save(usr);
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }
    @Override
    public List<Role> findAllRoles() {
        return roleRep.findAll();
    }
    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }
    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }
    @Override
    public User findUserById(Long id) {
        return userRep.findById(id).get();
    }
    @Override
    public Role findRoleById(Long id) {
        return roleRep.findRoleById(id);
    }
    @Override
    public User removeRoleFromUser(long id,Role r)
    {
        User user = userRep.findUserById(id);
        List<Role> listOfRoles = user.getRoles();
        listOfRoles.remove(r);
        userRep.save(user);
        return user;
    }
}