package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final Logger logger = LogManager.getLogger("UserService");

    /**
     * Method service used to save a new user. Username is unique attribute and
     * role must be ADMIN or USER.
     *
     * @param user
     * @return user
     */
    public User saveUser( User user) {

         if (userRepository
                .existsByUserName(user.getUserName())) {
            logger.error(
                    "ERROR: this username is already used.");
            return null;
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }
    /**
     * Method service used to find User by Id.
     *
     * @param id
     * @return user
     */
    public User findById( Integer id) {
        return userRepository.findUserById(id);
    }
    /**
     * Method service used to find all User.
     *
     * @return all users
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Method service used to update a user.
     *
     * @param user
     * @param id
     * @return User or null
     */
    public User updateUser( Integer id,User user) {


        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            logger.error("Unknown user with userName: {}", user.getUserName());
            return null;
        }
        String password = user.getPassword();
        String password2=existingUser.getPassword();
        if(password != "")
        {
            if (!(password.equals(password2)))
                existingUser.setPassword(encoder.encode(password));
        }
        existingUser.setFullName(user.getFullName());
        existingUser.setUserName(user.getUserName());
        existingUser.setRole(user.getRole().toUpperCase());
        User updatedUser=userRepository.save(existingUser);
        logger.info("User updated successfully!");
        return updatedUser;
    }

    /**
     * Method service used to delete a user with his userName.
     *
     * @param id
     * @return isDeleted boolean
     */
    public boolean deleteUser(Integer id) {
        boolean isDeleted = false;

        User existingUser = userRepository.findUserById(id);

        if (existingUser==null) {
            logger.error("Unknown user with id : {}", id);
            return isDeleted;
        }
        userRepository.delete(existingUser);
        logger.info("User deletd successfully!");
        isDeleted = true;
        return isDeleted;
    }


}
