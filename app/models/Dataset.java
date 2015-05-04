package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Dataset class represents one dataset from a Datasource like DataSF. Each
 * Dataset has a name, Fields with data, a String representing tags to match search queries,
 * and an API endpoint that can be used to make queries for its data.
 * @author michaelju
 *
 */

@Entity
public class Dataset extends Model {
    @Id
    private Long datasetId;
    private String name;
    private String apiEndPoint;
    private Integer _hash;
    
    @OneToMany(mappedBy="dataset")
    private Set<Field> fields;
    
    private String tags = "";
    
    @ManyToOne
    private Datasource datasource;
    
    public static Finder<Long, Dataset> find = 
            new Finder<Long, Dataset>(Long.class, Dataset.class);
    
    /**
     * Dataset constructor initiates a new Dataset with a name and an API 
     * endpoint. Also adds the Dataset's name as tags.
     * @param name is the name of the dataset.
     * @param api is the API endpoint.
     */
    public Dataset(String name, String api) {
        if (name == null || api == null) {
            throw new IllegalArgumentException("invalid dataset arguments");
        }
        this.name = name;
        apiEndPoint = api;
        fields = new HashSet<Field>();
        tags += name.toLowerCase();
    }
    
    /**** Getter methods are below for a Dataset's Id, name, Fields, and API endpoint. *****/
    
    /**
     * Get this Dataset's Id.
     * @return the Id.
     */
    public Long getId() {
        return datasetId;
    }
    
    /**
     * Get this Dataset's name.
     * @return name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get a defensive copy of this Dataset's Fields.
     * @return a set of the Dataset's fields.
     */
    public Set<Field> getFields() {
        Set<Field> ret = new HashSet<Field>();
        for (Field f : fields) {
            ret.add(f);
        }
        return ret;
    }
    
    /**
     * Get the API endpoint for this Dataset.
     * @return the endpoint url.
     */
    public String getEndPoint() {
        return apiEndPoint;
    }
    
    /**
     * Get this Dataset's Datasource.
     * @return the Datasource.
     */
    public Datasource getDatasource() {
        return datasource;
    }
    
    /**
     * Get an array of this Dataset's tags.
     * @return an array of tags.
     */
    public String getTags() {
        return tags;
    }
    
    /************************************/
    
    /**
     * Sets this dataset's Datasource. Can only be set once and is permanent.
     * @param d is the Datasource for this Dataset.
     * @return true if d was set to be this Dataset's Datasource, false otherwise.
     */
    public boolean setDatasource(Datasource d) {
        if (datasource != null) {
            return false;
        }
        if (d == null) {
            throw new IllegalArgumentException();
        }
        datasource = d;
        update();
        return true;
    }
    
    /**
     * Add a Field for this Dataset. A Field can only be added once to a Dataset.
     * @param f is the Field to be added.
     * @return true if the Field was added to this Dataset.
     */
    public boolean addField(Field f) {
        if (f == null) {
            throw new IllegalArgumentException("invalid field");
        }
        boolean canBeAdded = f.setDataset(this);
        if (canBeAdded) {
            tags += " " + f.getName().toLowerCase();
            update();
            fields.add(f);
            update();
            return true;
        }
        return false;
    }
    
    /**
     * Return the cached hash for this Dataset, determined by the Dataset's
     * name (which should be unique to it alone).
     */
    public int hashCode() {
        if (_hash == null) {
            _hash = name.hashCode();
        }
        return _hash;
    }
    
    /**
     * Create a Dataset and save it to the database.
     * @param d is the Dataset to be saved to the database.
     * @return d.
     */
    public static Dataset create(Dataset d) {
        if (d != null) {
            d.save();
        }
        return d;
    }
}
