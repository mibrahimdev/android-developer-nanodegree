package me.geekymind.moviedroid.data.entity;

import com.google.gson.annotations.SerializedName;

public class Review {

  @SerializedName("author")
  private String Author;
  @SerializedName("content")
  private String Content;
  @SerializedName("id")
  private String Id;
  @SerializedName("url")
  private String Url;

  public String getAuthor() {
    return Author;
  }

  public void setAuthor(String author) {
    Author = author;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getUrl() {
    return Url;
  }

  public void setUrl(String url) {
    Url = url;
  }
}
