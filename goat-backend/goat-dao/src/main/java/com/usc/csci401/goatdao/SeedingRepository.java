package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.Seeding;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeedingRepository extends JpaRepository<Seeding, Long> {

  Optional<Seeding> getByTournamentidAndUsername(Integer tournamentId, String username);

  @Query("select max(s.seed) from Seeding s where s.tournamentid = ?1")
  Integer getMaxSeeding(Integer tournament);

  Optional<Seeding> getByTournamentidAndSeed(Integer tournamentId, Integer seed);

  @Query(value = "SELECT s FROM Seeding s WHERE s.username = ?1")
  List<Seeding> getByUsername(String username);
}
