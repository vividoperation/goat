package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Modifying
  @Query(value = "update User user set user.email = ?2, user.phone = ?3 where user.username=?1")
  void updateById(String username, String email, long phone);


}
