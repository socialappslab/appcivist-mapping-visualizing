package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Datasource extends Model {
    @Id
    private Long datasourceID;
    private String name;
    
    @OneToMany(mappedBy="datasource")
    private Set<Dataset> datasets;
    
    public static Finder<Long, Datasource> find = 
            new Finder<Long, Datasource>(Long.class, Datasource.class);
    
    public Datasource(String name) {
        if (name == null) {
            throw new IllegalArgumentException("invalid datasource arguments");
        }
        this.name = name;
        datasets = new HashSet<Dataset>();
    }
    
    public boolean addDataset(Dataset d) {
        if (d == null) {
            throw new IllegalArgumentException("invalid dataset");
        }
        boolean canBeAdded = d.setDatasource(this);
        if (canBeAdded) {
            datasets.add(d);
            update();
            return true;
        }
        return false;
    }
    
    public Long getId() {
        return datasourceID;
    }
    
    public String getName() {
        return name;
    }
    
    public Set<Dataset> getDatasets() {
        Set<Dataset> ret = new HashSet<Dataset>();
        for (Dataset d : datasets) {
            ret.add(d);
        }
        return ret;
    }
    
    public static Datasource create(Datasource d) {
        if (d != null) {
            d.save();
        }
        return d;
    }
}
