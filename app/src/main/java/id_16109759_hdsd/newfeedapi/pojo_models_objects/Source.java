package id_16109759_hdsd.newfeedapi.pojo_models_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This object covers the 'Source' on the website
 * news.org API. example:
 * source": {
 * "id": "techcrunch",
 * "name": "TechCrunch"
 * },
 */

public class Source
{
    /**
     * Note we use SeializedName with the text 'id' as per the original JSON source,
     * This allows us to initialise out own variable name - something more appropriate
     * or the use of CamelCase
     * Source is an object within the Article array
     */

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    //Getters and Setters (Auto-generated)

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
