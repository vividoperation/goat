package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.GamePlayer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

  List<GamePlayer> findAllByGameid(Integer gameid);
}
