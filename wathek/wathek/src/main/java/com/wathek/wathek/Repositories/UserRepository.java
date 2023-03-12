package com.wathek.wathek.Repositories;

import com.wathek.wathek.Entities.Reclamation;
import com.wathek.wathek.Entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
