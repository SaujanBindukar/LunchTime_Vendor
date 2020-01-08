/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */
package bll;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eager {
    // private data member
    @SerializedName("transformation")
    @Expose
    private String transformation;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("bytes")
    @Expose
    private Integer bytes;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("secure_url")
    @Expose
    private String secureUrl;

    //getter method for transformation
    public String getTransformation() {
        return transformation;
    }

    //setter method for transformation
    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    //getter method for width
    public Integer getWidth() {
        return width;
    }

    //setter method for width
    public void setWidth(Integer width) {
        this.width = width;
    }

    //getter method for height
    public Integer getHeight() {
        return height;
    }
    //setter method for height
    public void setHeight(Integer height) {
        this.height = height;
    }

    //getter method for bytes
    public Integer getBytes() {
        return bytes;
    }

    //setter method for bytes
    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    //getter method for format
    public String getFormat() {
        return format;
    }

    //setter method for format
    public void setFormat(String format) {
        this.format = format;
    }

    //getter method for url
    public String getUrl() {
        return url;
    }

    //setter method for url
    public void setUrl(String url) {
        this.url = url;
    }

    //getter method for secureUrl
    public String getSecureUrl() {
        return secureUrl;
    }

    //setter method for secureUrl
    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }
}