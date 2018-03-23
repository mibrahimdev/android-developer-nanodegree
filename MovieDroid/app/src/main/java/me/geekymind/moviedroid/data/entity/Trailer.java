package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;

public class Trailer {

  @SerializedName("id")
  private String Id;
  @SerializedName("iso_3166_1")
  private String Iso31661;
  @SerializedName("iso_639_1")
  private String Iso6391;
  @SerializedName("key")
  private String Key;
  @SerializedName("name")
  private String Name;
  @SerializedName("site")
  private String Site;
  @SerializedName("size")
  private Long Size;
  @SerializedName("type")
  private String Type;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getIso31661() {
    return Iso31661;
  }

  public void setIso31661(String iso31661) {
    Iso31661 = iso31661;
  }

  public String getIso6391() {
    return Iso6391;
  }

  public void setIso6391(String iso6391) {
    Iso6391 = iso6391;
  }

  public String getKey() {
    return Key;
  }

  public void setKey(String key) {
    Key = key;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getSite() {
    return Site;
  }

  public void setSite(String site) {
    Site = site;
  }

  public Long getSize() {
    return Size;
  }

  public void setSize(Long size) {
    Size = size;
  }

  public String getType() {
    return Type;
  }

  public void setType(String type) {
    Type = type;
  }
}
