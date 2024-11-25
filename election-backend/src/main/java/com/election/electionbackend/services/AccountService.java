package com.election.electionbackend.services;

import com.election.electionbackend.Exceptions.ResourceNotFound;
import com.election.electionbackend.models.forum.Account;
import com.election.electionbackend.repositories.forum.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.springframework.util.StringUtils.hasLength;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @PersistenceContext
    private EntityManager em;




    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    /**
     * Returns a user entity, with the provided userName if it exists, otherwise null.
     */
    public Account findByName(String name) {
        return hasLength(name) ? accountRepository.findByUsername(name).orElse(null) : null;
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound(Account.class.getSimpleName(), "email", email));
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(Account.class.getSimpleName(), "id", id.toString()));
    }

    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    public boolean existsByDisplayName(String displayName) {
        return accountRepository.existsByDisplayName(displayName);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username - %s, not found", username)
                ));
    }

//    /**
//     * Creates a user based on the provided request.
//     */
//    public Account create(RegisterDto req) {
//        this.userRepository.findByUsername(req.getUsername()).ifPresent(g -> {
//            throw new ConflictException("Username already exists.");
//        });
//
//        var user = this.userRepository.save(req.toUser(encoder));
//
//        return roleService.addUserRoleToUserById(user.getId());
//    }

    public Account create(Account account) {
        if (
                findByEmail(account.getEmail()) == null
                && findByName(account.getUsername()) == null
        ) {  return accountRepository.save(account);}
      else return null;
    }


    public void remove(Account account) {
       em.remove(account);
    }

    public Account save(Account account) {
        if (account.getUserID() == null) {
            //insert a new affiliation
            em.persist(account);
        } else {
            //update an existing affiliation
            em.merge(account);
        }
        return account;
    }

//
//    private void updatePasswordIfPresent(UpdateDto updateDto, User user) {
//        if (hasText(updateDto.getPassword())) user.setPassword(encoder.encode(updateDto.getPassword()));
//    }

}
