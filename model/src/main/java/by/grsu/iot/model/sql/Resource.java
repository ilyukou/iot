package by.grsu.iot.model.sql;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
public class Resource extends BaseEntity {

    private String filename;

    @OneToOne(mappedBy = "resource")
    private Project project;

    public Resource(String fileName, Project project) {
        super();
        this.filename = fileName;
        this.project = project;
    }

    public Resource() {
        super();
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String fileName) {
        this.filename = fileName;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
