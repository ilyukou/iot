package by.grsu.iot.model.elastic;

import by.grsu.iot.model.sql.Project;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Entity used to work with the database for search, analog {@link Project}
 */
@Document(indexName = "project")
public class ProjectElasticsearch {

    @Id
    @Field(type = FieldType.Text, store = true)
    private String id;

    private Long owner;

    private Long projectId;

    private String name;

    private String title;

    public ProjectElasticsearch(String id, Long owner, Long projectId, String name, String title) {
        this.id = id;
        this.owner = owner;
        this.projectId = projectId;
        this.name = name;
        this.title = title;
    }

    public ProjectElasticsearch(Project project) {
        if (project.getUser() != null) {
            this.owner = project.getUser().getId();
        }
        this.projectId = project.getId();
        this.name = project.getName();
        this.title = project.getTitle();
    }

    public ProjectElasticsearch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
