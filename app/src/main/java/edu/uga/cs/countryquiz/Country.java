package edu.uga.cs.countryquiz;

public class Country {

    private int id;
    private String country;
    private String continent;


    public Country ()
    {
        this.id = -1;
        this.country = null;
        this.continent = null;
    }

    public Country ( String country, String continent ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.country = country;
        this.continent = continent;
    }

    public long getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCountry()
    {
        return country;
    }

    public String getContinent()
    {
        return continent;
    }

    public String toString()
    {
        return id + ": " + country + " " + continent;
    }
}

