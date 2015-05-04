package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Datasource class represents an open data source for a city/location, like
 * OpenDataSF. Each Datasource has a name and a set of Datasets with data on various
 * city data, like crime and housing.
 * @author michaelju
 *
 */

@Entity
public class Datasource extends Model {
    @Id
    private Long datasourceID;
    private String name;
    
    @OneToMany(mappedBy="datasource")
    private Set<Dataset> datasets;
    
    public static Finder<Long, Datasource> find = 
            new Finder<Long, Datasource>(Long.class, Datasource.class);
    
    /**
     * Basic constructor instantiating a new Datasource with a name.
     * @param name is the Datasource's name.
     */
    public Datasource(String name) {
        if (name == null) {
            throw new IllegalArgumentException("invalid datasource arguments");
        }
        this.name = name;
        datasets = new HashSet<Dataset>();
    }
    
    /*******Getter methods are below for a Datasource's Id, name, and Datasets.************/
    
    /**
     * Get this Datasource's Id.
     * @return the Id.
     */
    public Long getId() {
        return datasourceID;
    }
    
    /**
     * Get this Datasource's name.
     * @return the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get this Datasource's Datasets.
     * @return a defensive copy set of this Datasource's Datasets.
     */
    public Set<Dataset> getDatasets() {
        Set<Dataset> ret = new HashSet<Dataset>();
        for (Dataset d : datasets) {
            ret.add(d);
        }
        return ret;
    }
    
    /**************************************/
    
    /**
     * Add a Dataset to this Datasource. A Dataset can only be added to one 
     * dataset.
     * @param d is the Dataset to be added. 
     * @return true if d was added to this Datasource.
     */
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
    
    /**
     * Save a Datasource into the database.
     * @param d is the Datasource to be saved.
     * @return d.
     */
    public static Datasource create(Datasource d) {
        if (d != null) {
            d.save();
        }
        return d;
    }
}
