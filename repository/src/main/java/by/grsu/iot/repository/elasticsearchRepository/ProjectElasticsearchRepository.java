package by.grsu.iot.repository.elasticsearchRepository;

import by.grsu.iot.model.elastic.ProjectElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectElasticsearchRepository extends ElasticsearchRepository<ProjectElasticsearch, String> {
    List<ProjectElasticsearch> findByNameAndTitle(String name, String title);

    ProjectElasticsearch findByProjectId(Long projectId);
}
