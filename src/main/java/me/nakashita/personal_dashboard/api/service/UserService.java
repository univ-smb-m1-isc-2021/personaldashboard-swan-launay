package me.nakashita.personal_dashboard.api.service;

import javax.transaction.Transactional;

import me.nakashita.personal_dashboard.api.model.User;
import me.nakashita.personal_dashboard.api.repository.UserRepository;
import me.nakashita.personal_dashboard.security.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repo;

    public void updateAuthenticationType(String username, String oauth2ClientName) {
        AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
        repo.updateAuthenticationType(username, authType);
        System.out.println("Updated user's authentication type to " + authType);
    }

    public boolean isExist(String username) {
        return repo.getUserByUsername(username) != null;
    }

    public User saveUser(String username, String password, String name, AuthenticationType authType) {
        return repo.save(new User(username, password, name));
    }

    public User saveUser(String username, String password, String name) {
        return repo.save(new User(username, password, name));
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public User getUserByUsername(String username) {
        return repo.getUserByUsername(username);
    }

    public User getCurrentUser(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return repo.getUserByUsername(principal.getName());
    }

    public boolean currentUserOwnGroup(Long groupId){
        return getCurrentUser().ownGroup(groupId);
    }

    public void deleteGroupById(Long groupId) {
        User user = getCurrentUser();
        user.deleteGroupById(groupId);
        repo.save(user);
    }
}
