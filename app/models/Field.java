package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Field class represents one column of a Dataset. Each Field has a name, a Dataset
 * it belongs to, an API name used to make queries on it, and a type (like amount, 
 * percent, quality) used to evaluate which Visualizations are available to a
 * user based on his/her Field selections.
 * @author michaelju
 *
 */
@Entity
public class Field extends Model {
    @Id
    private long fieldId;
    private String name;
    private String apiName;
    private String type;
    private Integer _hash;
    
    @ManyToOne
    private Dataset dataset;
    
    private static Set<String> acceptableTypes;
    static {
        acceptableTypes = new HashSet<String>();
        
        //some basic field types; more can be added later
        acceptableTypes.add("quality");
        acceptableTypes.add("amount");
        acceptableTypes.add("percent");
        acceptableTypes.add("date/time");
        acceptableTypes.add("location");
    }
    
    public static Finder<Long, Field> find = new Finder<Long, Field>(Long.class, Field.class);
    
    /**
     * Basic Field constructor instantiates a new Field with a name, API name, and
     * type. Makes sure that the type is valid.
     * @param name is the Field's name.
     * @param type is the Field's type. Must be one of the types in acceptableTypes.
     * @param apiName is the Field's API name, used to make queries for this Field.
     */
    public Field(String name, String type, String apiName) {
        if (name == null || type == null || apiName == null
                || !acceptableTypes.contains(type)) {
            throw new IllegalArgumentException("invalid field argument");
        }
        this.name = name;
        this.type = type;
        this.apiName = apiName;
    }
    
    /*****Getter methods are below for a Field's Id, name, type, API name, and Dataset.*******/
    
    /**
     * Get this Field's Id.
     * @return the Id.
     */
    public Long getId() {
        return fieldId;
    }
    
    /**
     * Get this Field's name.
     * @return the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get this Field's type.
     * @return the type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Get this Field's API name.
     * @return the API name.
     */
    public String getApiName() {
        return apiName;
    }
    
    /**
     * Get this Field's Dataset.
     * @return the Dataset.
     */
    public Dataset getDataset() {
        return dataset;
    }
    
    /*****************************************/
    
    /**
     * Set the Dataset to which this Field belongs. Can only be done once and is
     * permanent.
     * @param d is the Dataset to set for this Field.
     * @return true if this Field's Dataset was set to d.
     */
    public boolean setDataset(Dataset d) {
        if (dataset != null) {
            return false;
        }
        if (d == null) {
            throw new IllegalArgumentException("invalid dataset");
        }
        dataset = d;
        update();
        return true;
    }
    
    /**
     * Return the cached hash for this Field, which is based on its name.
     */
    public int hashCode() {
        if (_hash == null) {
            _hash = name.hashCode();
        }
        return _hash;
    }
    
    /**
     * Save a Field into the database.
     * @param f is the Field to be saved.
     * @return f.
     */
    public static Field create(Field f) {
        if (f != null) {
            f.save();
        }
        return f;
    }   
}
