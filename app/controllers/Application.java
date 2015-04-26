package controllers;

import play.*;
import play.mvc.*;
import models.*;
import views.html.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result findLocation(String query) {
        query = query.replaceAll("\\+", " ");
        List<Datasource> result = Datasource.find.where().ilike("name", query).findList();
        if (result.isEmpty()) {
            return ok("No datasource found for requested city. Sorry!");
        }
        Datasource d = result.get(0);
        return ok(d.getName() + "\n" + d.getId());
    }
    
    public static Result search(Long id, String query) {
        Datasource d = Datasource.find.byId(id);
        if (d == null) {
            return notFound("Datasource you requested does not exist");
        }
        Set<Dataset> matching = findDatasets(d, query);
        if (matching.isEmpty()) {
            return ok("No datasets found for search query. Sorry!");
        }
        String result = "";
        for (Dataset set : matching) {
            result += set.getName() + "\n";
        }
        return ok(result);
    }
    
    private static Set<Dataset> findDatasets(Datasource d, String query) {
        String[] words = query.split("\\+");
        Set<String> keyWords = new HashSet<String>();
        for (String word : words) {
            keyWords.add(word.toLowerCase());
        }
        Set<Dataset> ret = new HashSet<Dataset>();
        String[] tags;
        
        for (Dataset set : d.getDatasets()) {
            tags = generateTags(set);
            if (intersects(tags, keyWords)) {
                ret.add(set);
            }
        } 
        return ret;
    }
    
    private static String[] generateTags(Dataset set) {
        String template = set.getName();
        for (Field f : set.getFields()) {
            template += " " + f.getName();
        }
        template = template.toLowerCase();
        return template.split(" ");       
    }
    
    private static boolean intersects(String[] tags, Set<String> keys) {
        for (String tag : tags) {
            if (keys.contains(tag)) {
                return true;
            }
        }
        return false;
    }

}
