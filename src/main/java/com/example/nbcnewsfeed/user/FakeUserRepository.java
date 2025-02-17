package com.example.nbcnewsfeed.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface FakeUserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long userId){
        return findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    };
}
