package id_16109759_hdsd.newfeedapi.api_interface;

import id_16109759_hdsd.newfeedapi.pojo_models_objects.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Data Sourced from
 * https://newsapi.org/docs/endpoints/everything
 */

public interface ApiService
{
    @GET("top-headlines")
    Call<News> getNews(

            @Query("country") String country ,
            @Query("apiKey") String apiKey

    );
    @GET("everything")
     Call<News> getEverything(

            // Request parameters per API Documentation:
            // https://newsapi.org/docs/endpoints/everything
            @Query("q") String keyword,
//            @Query("sources") String sources,
//            @Query("domains") String domains,
//            @Query("excludeDomains") String excludedDomains,
//            @Query("from") String dateFrom,
//            @Query("to") String dateTo,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
//            @Query("pageSize") String pageSize,
//            @Query("page") String page,
            @Query("apiKey") String apikey
    );

}
