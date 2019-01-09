package id_16109759_hdsd.newfeedapi.pojo_models_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data Sourced from:
 * https://newsapi.org/docs/endpoints/everything
 * Note: this is the basic structure retrieved from the JSON file
 */
public class News
{
    /**
     * Note we use SeializedName with the text 'id' as per the original JSON source,
     * This allows us to initialise out own variable name - something more appropriate
     * or the use of CamelCase
     * Below contains the main objects retrieved from the API
     */

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResult")
    @Expose
    private int totalResult;

    @SerializedName("articles")
    @Expose
    private List<Article> article;

    //Getters and Setters (Auto-generated)

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getTotalResult()
    {
        return totalResult;
    }

    public void setTotalResult(int totalResult)
    {
        this.totalResult = totalResult;
    }

    public List<Article> getArticle()
    {
        return article;
    }

    public void setArticle(List<Article> article)
    {
        this.article = article;
    }
}
