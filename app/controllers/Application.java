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
    
    /**
     * Find the Datasource corresponding to the location input by the user.
     * @param query is the user's search query.
     * @return 
     */
    public static Result findLocation(String query) {
        query = query.replaceAll("\\+", " ");
        List<Datasource> result = Datasource.find.where().ilike("name", query).findList();
        if (result.isEmpty()) {
            return ok("No datasource found for requested city. Sorry!");
        }
        Datasource d = result.get(0);
        return ok(d.getName() + "\n" + d.getId());
    }
    
    /**
     * Search for relevant Datasets for a user's search query.
     * @param id is the Id of the Datasource in which we are searching.
     * @param query is the user's search query.
     * @return
     */
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
    
    /**
     * Helper method for the search method above. Finds Datasets with 
     * Tags matching the words in the user's query. 
     * @param d is the Datasource we are searching.
     * @param query is the user's search query.
     * @return a Set of relevant Datasets.
     */
    private static Set<Dataset> findDatasets(Datasource d, String query) {
        String[] words = query.split("\\+");
        Set<String> keyWords = new HashSet<String>();
        for (String word : words) {
            keyWords.add(word.toLowerCase());
        }
        Set<Dataset> ret = new HashSet<Dataset>();
        String tags;
        for (Dataset set : d.getDatasets()) {
            tags = set.getTags();
            if (intersects(tags, keyWords)) {
                ret.add(set);
            }
        } 
        return ret;
    }
    
    /**
     * Helper method for findDatasets above. Checks whether a set of Tags
     * has words in common with a set of words.
     * @param tags is the String of tags to be checked.
     * @param keys is the set of words to be checked.
     * @return true if there is an intersection.
     */
    private static boolean intersects(String tags, Set<String> keys) {
        String[] tagArray = tags.split(" ");
        for (String tag : tagArray) {
            if (keys.contains(tag)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the Fields for a particular Dataset.
     * @param datasourceId is the Id of the Datasource we are currently using.
     * @param datasetId is the the Id of the Dataset for which we want Fields.
     * @return
     */
    public static Result getFields(Long datasourceId, Long datasetId) {
        String ret = "";
        Dataset d = Dataset.find.byId(datasetId);
        for (Field f : d.getFields()) {
            ret += f.getName() + "\n";
        }
        return ok(ret);
    }

}
