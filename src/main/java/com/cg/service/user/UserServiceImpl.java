package com.cg.service.user;

import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.UserPrinciple;
import com.cg.model.dto.UserDTO;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserDTO> findUserDTOByEmail(String email) {
        return userRepository.findUserDTOByEmail(email);
    }

    @Override
    public Optional<UserDTO> findUserDTOByEmailPassword(String email) {
        return userRepository.findUserDTOByEmailPassword(email);
    }

    @Override
    public Optional<UserDTO> findUserDTOByPhone(String phone) {
        return userRepository.findUserDTOByPhone(phone);
    }

    @Override
    public Optional<UserDTO> findUserDTOById(long id) {
        return userRepository.findUserDTOById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LocationRegion locationRegion = locationRegionRepository.save(user.getLocationRegion());
        user.setLocationRegion(locationRegion);
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        return UserPrinciple.build(userOptional.get());
    }
}
