/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Farley Tran
 */
public class FilmDatabase implements Serializable
{
    private static final long serialVersionUID = 1786780025347509966L;
    private static int count;
    private int filmID;
    private String name;
    private boolean favorite;
    private boolean bluray;
    private String trailerLink;
    private String description;
    private String image1, image2, image3;
    private int season;
    private String episodes;

    public static int getCount()
    {
        return count;
    }

    public static void setCount(int count)
    {
        FilmDatabase.count = count;
    }
    private String background;
    private String movieLocation;
    private String movieSeries;

    public String getMovieSeries()
    {
        return movieSeries;
    }

    public void setMovieSeries(String movieSeries)
    {
        this.movieSeries = movieSeries;
    }

    public FilmDatabase(FilmDatabase fd)
    {
        this.name = fd.getName();
        this.filmID = fd.getFilmID();
        this.favorite = fd.isFavorite();
        this.bluray = fd.isBluray();
        this.trailerLink = fd.getTrailerLink();
        this.description = fd.getDescription();
        this.image1 = fd.getImage1();
        this.image2 = fd.getImage2();
        this.image3 = fd.getImage3();
        this.season = fd.getSeason();
        this.episodes = fd.getEpisodes();
        this.background = fd.getBackground();
        this.movieLocation = fd.getMovieLocation();
        this.movieSeries = fd.getMovieSeries();
    }
    public FilmDatabase(String name, String movieLocation, boolean favorite, 
            boolean bluray, String trailerLink, String description, 
            String image1, String image2, String image3, int season, 
            String episodes, String background)
    {
        String path = System.getProperty("user.dir");
        path = path.replace('\\', '/');
        path += "/build/classes/data/filmDatabase/";
        this.filmID = count;
        count++;
        if (name == null)
        {
            this.name = "N/A";
        } else
        {
            this.name = name;
        }
        if (movieLocation == null || movieLocation.equals(""))
        {
            this.movieLocation = null;
        } else
        {
            this.movieLocation = movieLocation;
        }
        if (trailerLink == null)
        {
            this.trailerLink = "https://www.youtube.com/watch?v=7wtfhZwyrcc";
        } else
        {
            this.trailerLink = trailerLink;
        }
        if (description == null)
        {
            this.description = "N/A";
        } else
        {
            this.description = description;
        }
        if (image1 == null)
        {
            this.image1 = path+"umaru1.png";
        } else
        {
            this.image1 = image1;
        }
        if (image2 == null)
        {
            this.image2 = path+"umaru2.png";
        } else
        {
            this.image2 = image2;
        }
        if (image3 == null)
        {
            this.image3 = path+"umaru3.png";
        } else
        {
            this.image3 = image3;
        }
        if (background == null)
        {
            background = path+"background.png";
        } else
        {

            this.background = background;
        }
        this.season = season;
        this.episodes = episodes;
        this.favorite = favorite;
        this.bluray = bluray;
        movieSeries = "None";
    }

    public String getMovieLocation()
    {
        return movieLocation;
    }

    public void setMovieLocation(String movieLocation)
    {
        this.movieLocation = movieLocation;
    }

    public int getFilmID()
    {
        return filmID;
    }

    public void setFilmID(int filmID)
    {
        this.filmID = filmID;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public boolean isBluray()
    {
        return bluray;
    }

    public void setBluray(boolean bluray)
    {
        this.bluray = bluray;
    }

    public String getTrailerLink()
    {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink)
    {
        this.trailerLink = trailerLink;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImage1()
    {
        return image1;
    }

    public void setImage1(String image1)
    {
        this.image1 = image1;
    }

    public String getImage2()
    {
        return image2;
    }

    public void setImage2(String image2)
    {
        this.image2 = image2;
    }

    public String getImage3()
    {
        return image3;
    }

    public void setImage3(String image3)
    {
        this.image3 = image3;
    }

    public int getSeason()
    {
        return season;
    }

    public void setSeason(int season)
    {
        this.season = season;
    }

    public String getEpisodes()
    {
        return episodes;
    }

    public void setEpisodes(String episodes)
    {
        this.episodes = episodes;
    }

    public String getBackground()
    {
        return background;
    }

    public void setBackground(String background)
    {
        this.background = background;
    }

}
