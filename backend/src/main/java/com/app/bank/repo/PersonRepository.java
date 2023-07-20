package com.app.bank.repo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.bank.model.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, ObjectId> {

    Optional<Person> getUserByUserID(String userID);

}
