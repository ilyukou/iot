package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elastic.ProjectElasticsearch;
import by.grsu.iot.repository.interf.ProjectElasticsearchRepository;
import by.grsu.iot.repository.jpa.ProjectElasticsearchJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ProjectElasticsearchRepositoryImpl implements ProjectElasticsearchRepository {

    private final ProjectElasticsearchJpaRepository projectElasticsearchJpaRepository;

    public ProjectElasticsearchRepositoryImpl(ProjectElasticsearchJpaRepository projectElasticsearchJpaRepository) {
        this.projectElasticsearchJpaRepository = projectElasticsearchJpaRepository;
    }

    @Override
    public List<ProjectElasticsearch> search(String query) {
        return projectElasticsearchJpaRepository.findByNameAndTitle(query, query);
    }
}
