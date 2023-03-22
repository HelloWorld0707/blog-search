package blog.search.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Rank> findByQuery(String query);
}
