package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.Game;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  @Modifying
  @Query(value = "update Game game set game.startgameurl = ?2, game.creategameurl = ?3 where game.name=?1")
  void updateById(String gameName, String startUrl, String backendUrl);

  Optional<Game> findByName(String gameName);

}
