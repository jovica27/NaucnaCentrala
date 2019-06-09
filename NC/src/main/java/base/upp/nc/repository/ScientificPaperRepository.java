package base.upp.nc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import base.upp.nc.domain.ScientificPaper;

@Repository
public interface ScientificPaperRepository extends JpaRepository<ScientificPaper, Long> {

}
