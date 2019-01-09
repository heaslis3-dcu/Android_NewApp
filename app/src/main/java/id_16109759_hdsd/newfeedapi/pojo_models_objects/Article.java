package id_16109759_hdsd.newfeedapi.pojo_models_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This object covers all objects under 'Article' Array on the website
 * news.org API. example:
 * NOTE: source has its own class as it contains id & name,
 * {
 * +"source": { … },
 * "author": "Kate Clark",
 * "title": "NYSE operator’s crypto project Bakkt brings in $182M",
 * "description": "Venture capitalists remain bullish on Bitcoin and its underlying technology despite sinking crypto prices.",
 * "url": "http://techcrunch.com/2018/12/31/nyse-operators-crypto-project-bakkt-brings-in-182m/",
 * "urlToImage": "https://techcrunch.com/wp-content/uploads/2018/12/GettyImages-1064373142.jpg?w=600",
 * "publishedAt": "2018-12-31T18:56:47Z",
 * "content": "The Intercontinental Exchange’s (ICE) cryptocurrency project Bakkt celebrated New Year’s Eve with the announcement of a $182.5 million equity round from a slew of notable institutional investors. ICE, the operator of several global exchanges, including the Ne… [+3472 chars]"
 * },
 */
public class Article
{
    /**
     * Note we use SeializedName with the text 'id' as per the original JSON source,
     * This allows us to initialise out own variable name - something more appropriate
     * or the use of CamelCase
     * Article is an array containing the below object (Source) and Strings
     */

    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt)
    {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    //Getters and Setters (Auto-generated)

    public Source getSource()
    {
        return source;
    }

    public void setSource(Source source)
    {
        this.source = source;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlToImage()
    {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage)
    {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }
}
