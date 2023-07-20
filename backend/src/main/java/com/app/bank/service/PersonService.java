package com.app.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bank.model.Person;
import com.app.bank.repo.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository userRepository;

    public List<Person> getAllUsers() {
        return userRepository.findAll();
    }

    public void newUser(Person user) {
        userRepository.insert(user);
    }

    public boolean checkforUser(Person user) {
        return userRepository.getUserByUserID(user.getUserID()).isPresent();
    }

    public boolean checkforUserPassword(Person user) {
        if (checkforUser(user)) {
            Person user1 = userRepository.getUserByUserID(user.getUserID()).get();
            if (user1.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public Person getUser(String userID) {
        if (userRepository.getUserByUserID(userID).isPresent()) {
            return userRepository.getUserByUserID(userID).get();
        }
        return null;
    }

    public void deleteUser(String userID) {
        if (userRepository.getUserByUserID(userID).isPresent()) {
            userRepository.delete(userRepository.getUserByUserID(userID).get());
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
