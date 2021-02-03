package by.grsu.iot.repository.interf;

import by.grsu.iot.model.elastic.ProjectElasticsearch;

import java.util.List;

public interface ProjectElasticsearchRepository {

    /**
     * Search for projects by text query
     *
     * @param query any text
     * @return {@link List} of {@link ProjectElasticsearch}
     */
    List<ProjectElasticsearch> search(String query);
}
