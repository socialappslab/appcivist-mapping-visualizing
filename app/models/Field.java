package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

/**
 * Field class should be immutable, save for permanently setting the
 * Field's Dataset once.
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
    
    public Field(String name, String type, String apiName) {
        if (name == null || type == null || apiName == null
                || !acceptableTypes.contains(type)) {
            throw new IllegalArgumentException("invalid field argument");
        }
        this.name = name;
        this.type = type;
        this.apiName = apiName;
    }
    
    public Long getId() {
        return fieldId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public String getApiName() {
        return apiName;
    }
    
    public Dataset getDataset() {
        return dataset;
    }
    
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
    
    public int hashCode() {
        if (_hash == null) {
            _hash = name.hashCode();
        }
        return _hash;
    }
    
    public static Field create(Field f) {
        if (f != null) {
            f.save();
        }
        return f;
    }   
}
