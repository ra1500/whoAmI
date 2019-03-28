package main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDAO extends JpaRepository<InquiryEntity, Long> {
}
