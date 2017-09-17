package my_parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Salary {

    @SerializedName("to")
    @Expose
    private Object to;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("gross")
    @Expose
    private Boolean gross;

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getGross() {
        return gross;
    }

    public void setGross(Boolean gross) {
        this.gross = gross;
    }

}