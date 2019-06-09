package base.upp.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import base.upp.nc.domain.Authority;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority, Long> {

}
