package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.TokenMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenMappingRepository extends JpaRepository<TokenMapping, Long>{

  Optional<TokenMapping> findByToken(Integer token);

  void deleteByGameid(Integer gameId);

}