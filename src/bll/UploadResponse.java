/**
 * @author Saujan Bindukar
 * A Java class which is a fully encapsulated class.
 * It has a private data member and getter and setter methods.
 */

package bll;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    // private data member
    @SerializedName("public_id")
    @Expose
    private String publicId;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("resource_type")
    @Expose
    private String resourceType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("bytes")
    @Expose
    private Integer bytes;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("placeholder")
    @Expose
    private Boolean placeholder;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("secure_url")
    @Expose
    private String secureUrl;
    @SerializedName("overwritten")
    @Expose
    private Boolean overwritten;
    @SerializedName("original_filename")
    @Expose
    private String originalFilename;
    @SerializedName("eager")
    @Expose
    private List<Eager> eager = null;

    //getter method for publicId
    public String getPublicId() {
        return publicId;
    }

    //setter method for
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    //getter method for version
    public Integer getVersion() {
        return version;
    }

    //setter method for version
    public void setVersion(Integer version) {
        this.version = version;
    }

    //getter method for signature
    public String getSignature() {
        return signature;
    }

    //setter method for signature
    public void setSignature(String signature) {
        this.signature = signature;
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

    //getter method for format
    public String getFormat() {
        return format;
    }

    //setter method for format
    public void setFormat(String format) {
        this.format = format;
    }

    //getter method for resourceType
    public String getResourceType() { return resourceType; }

    //setter method for resourceType
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    //getter method for createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    //setter method for createdAt
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //getter method for tags
    public List<Object> getTags() {
        return tags;
    }

    //setter method for tags
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    //getter method for bytes
    public Integer getBytes() {
        return bytes;
    }

    //setter method for bytes
    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    //getter method for type
    public String getType() {
        return type;
    }

    //setter method for type
    public void setType(String type) {
        this.type = type;
    }

    //getter method for etag
    public String getEtag() {
        return etag;
    }

    //setter method for etag
    public void setEtag(String etag) {
        this.etag = etag;
    }

    //getter method for placeholder
    public Boolean getPlaceholder() {
        return placeholder;
    }

    //setter method for placeholder
    public void setPlaceholder(Boolean placeholder) {
        this.placeholder = placeholder;
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

    //getter method for overwritten
    public Boolean getOverwritten() {
        return overwritten;
    }

    //setter method for overwritten
    public void setOverwritten(Boolean overwritten) {
        this.overwritten = overwritten;
    }

    //getter method for originalFilename
    public String getOriginalFilename() {
        return originalFilename;
    }

    //setter method for originalFilename
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    //getter method for eager
    public List<Eager> getEager() {
        return eager;
    }

    //setter method for eager
    public void setEager(List<Eager> eager) {
        this.eager = eager;
    }

}