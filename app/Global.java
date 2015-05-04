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
        
//        d = Dataset.create(new Dataset("Street Names", 
//                "https://data.sfgov.org/resource/6d9h-4u5v.json"));
//        f = Field.create(new Field("Street Name", "quality", "fullstreetname"));
//        d.addField(f);
//        f = Field.create(new Field("Street Type", "quality", "streettype"));
//        d.addField(f);
//        source.addDataset(d);
        
//        d = Dataset.create(new Dataset("Disabled Parking", 
//                "https://data.sfgov.org/resource/wc6f-brai.json"));
//        f = Field.create(new Field("Address", "quality", "address"));
//        d.addField(f);
//        f = Field.create(new Field("Cross Street", "quality", "crossst"));
//        d.addField(f);
//        f = Field.create(new Field("Location", "location", "location"));
//        d.addField(f);
//        source.addDataset(d);
//        
//        d = Dataset.create(new Dataset("Mobile Food Facility Permit", 
//                "https://data.sfgov.org/resource/rqzj-sfat.json"));
//        f = Field.create(new Field("Applicant", "quality", "applicant"));
//        d.addField(f);
//        f = Field.create(new Field("Facility Type", "quality", "facilitytype"));
//        d.addField(f);
//        f = Field.create(new Field("Status", "quality", "status"));
//        d.addField(f);
//        f = Field.create(new Field("Approved Date/Time", "date/time", "approved"));
//        d.addField(f);
//        f = Field.create(new Field("Expiration Date", "date/time", "expirationdate"));
//        d.addField(f);
//        f = Field.create(new Field("Location", "location", "location"));
//        d.addField(f);
//        source.addDataset(d);
//        
//        d = Dataset.create(new Dataset("Recreation & Park Department Park Info Dataset",
//                "https://data.sfgov.org/resource/z76i-7s65.json"));
//        f = Field.create(new Field("Park Name", "quality", "parkname"));
//        d.addField(f);
//        f = Field.create(new Field("Park Type", "quality", "parktype"));
//        d.addField(f);
//        f = Field.create(new Field("Park Service Area", "quality", "parkservicearea"));
//        d.addField(f);
//        f = Field.create(new Field("Acreage", "amount", "acreage"));
//        d.addField(f);
//        f = Field.create(new Field("SupDistrict", "quality", "supdist"));
//        d.addField(f);
//        f = Field.create(new Field("Location", "location", "location_1"));
//        d.addField(f);
//        source.addDataset(d);
//        
        d = Dataset.create(new Dataset("Budget", 
                "https://data.sfgov.org/resource/xdgd-c79v.json"));
        f = Field.create(new Field("Fiscal Year", "date/time", "fiscal_year"));
        d.addField(f);
        f = Field.create(new Field("Revenue or Spending", "quality", "revenue_or_spending"));
        d.addField(f);
        f = Field.create(new Field("Organization Group", "quality", "organization_group"));
        d.addField(f);
        f = Field.create(new Field("Department", "quality", "department"));
        d.addField(f);
        f = Field.create(new Field("Fund", "quality", "fund"));
        d.addField(f);
        f = Field.create(new Field("Fund Category", "quality", "fund_category"));
        d.addField(f);
        f = Field.create(new Field("Amount", "amount", "amount"));
        d.addField(f);
        source.addDataset(d);
        
        
    }
}