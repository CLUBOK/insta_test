package com.example.insta_test.models;



public class Picture
{
    public String id;
    public String title;
    public int likesCount;
    public String pictureUrl;


    public Picture()
    {

    }

    public Picture(String id, String title, int likesCount, String fileUrl)
    {
        this.id = id;
        this.title = title;
        this.likesCount = likesCount;
        this.pictureUrl = fileUrl;
    }


    @Override
    public String toString()
    {
        return '\n' + "Picture{" +
                '\n' + "id=" + id +
                '\n' + "title=" + title +
                '\n' + "likesCount=" + likesCount +
                '\n' + "pictureUrl=" + pictureUrl +
                '\n' + '}';
    }
}
