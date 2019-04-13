package db.repository;

import db.entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepositoryDAO extends JpaRepository<InquiryEntity, Long> {

    InquiryEntity findOneByGid(Long gid);


}
