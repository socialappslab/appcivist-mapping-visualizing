package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Dataset extends Model {
    @Id
    private Long datasetId;
    private String name;
    private String apiEndPoint;
    private Integer _hash;
    
    @OneToMany(mappedBy="dataset")
    private Set<Field> fields;
    
    @ManyToOne
    private Datasource datasource;
    
    public static Finder<Long, Dataset> find = 
            new Finder<Long, Dataset>(Long.class, Dataset.class);
    
    public Dataset(String name, String api) {
        if (name == null || api == null) {
            throw new IllegalArgumentException("invalid dataset arguments");
        }
        this.name = name;
        apiEndPoint = api;
        fields = new HashSet<Field>();        
    }
    
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
    
    public boolean addField(Field f) {
        if (f == null) {
            throw new IllegalArgumentException("invalid field");
        }
        boolean canBeAdded = f.setDataset(this);
        if (canBeAdded) {
            fields.add(f);
            update();
            return true;
        }
        return false;
    }
    
    public Long getId() {
        return datasetId;
    }
    
    public String getName() {
        return name;
    }
    
    public Set<Field> getFields() {
        Set<Field> ret = new HashSet<Field>();
        for (Field f : fields) {
            ret.add(f);
        }
        return ret;
    }
    
    public String getEndPoint() {
        return apiEndPoint;
    }
    
    public int hashCode() {
        if (_hash == null) {
            _hash = name.hashCode();
        }
        return _hash;
    }
    
    public static Dataset create(Dataset d) {
        if (d != null) {
            d.save();
        }
        return d;
    }
}
