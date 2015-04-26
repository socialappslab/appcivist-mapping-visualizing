import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings {
    public void onStart(Application app) {
        Datasource source;
        Dataset d;
        Field f;
        
        source = Datasource.create(new Datasource("San Francisco"));
        
        d = Dataset.create(new Dataset("SFPD Incidents - from 1 January 2003", 
                "https://data.sfgov.org/resource/tmnf-yvry.json"));
        f = Field.create(new Field("Category", "quality", "category"));
        d.addField(f);
        f = Field.create(new Field("Day of Week", "quality", "dayofweek"));
        d.addField(f);
        f = Field.create(new Field("Date", "date/time", "date"));
        d.addField(f);
        f = Field.create(new Field("Time", "date/time", "time"));
        d.addField(f);
        f = Field.create(new Field("Pd District", "quality", "pddistrict"));
        d.addField(f);
        f = Field.create(new Field("Resolution", "quality", "resolution"));
        d.addField(f);
        f = Field.create(new Field("Location", "location", "location"));
        d.addField(f);
        source.addDataset(d);       
    }
}