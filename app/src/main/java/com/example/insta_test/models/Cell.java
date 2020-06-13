package com.example.insta_test.models;



import java.util.List;

public class Cell
{
    public String id;
    public String name;
    public String subTitle;
    public String avatarUrl;
    public int likesCount;
    public List<Picture> pictures;


    public Cell()
    {

    }


    @Override
    public String toString()
    {
        return "Cell{" +
                '\n' + "id=" + id +
                '\n' +"name=" + name +
                '\n' + "subTitle=" + subTitle +
                '\n' + "avatarUrl=" + avatarUrl +
                '\n' + "likesCount=" + likesCount +
                '\n' + "pictures=" + pictures +
                '\n' + '}';
    }
}
