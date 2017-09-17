package my_parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("items")
    @Expose
    private spec_item items;

    @SerializedName("per_page")
    @Expose
    private String per_page;

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("found")
    private int found;


}
