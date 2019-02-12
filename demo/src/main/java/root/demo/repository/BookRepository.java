package root.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import root.demo.lucene.model.IndexUnit;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<IndexUnit, String> {

	List<IndexUnit> findByTitle(String title);

	IndexUnit findByFilename(String filename);
}
