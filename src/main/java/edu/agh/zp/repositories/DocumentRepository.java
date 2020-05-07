package edu.agh.zp.repositories;

import edu.agh.zp.objects.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
	@Query (value="Select * from document WHERE (doctypeid=1 or doctypeid=4 or doctypeid=7) and (docstatusid=1 or docstatusid=8 or docstatusid=9)", nativeQuery=true)
	List<DocumentEntity> findByDocForSejm( );

	@Query (value="Select * from document WHERE (doctypeid=1 or doctypeid=4 or doctypeid=7) and (docstatusid=1 or docstatusid=10)", nativeQuery=true)
	List<DocumentEntity> findByDocForSenat( );

	Optional<DocumentEntity> findByDocID(long docID);
}
