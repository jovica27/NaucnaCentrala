package base.upp.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import base.upp.nc.domain.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

	Magazine findOneById(Long id);
}
