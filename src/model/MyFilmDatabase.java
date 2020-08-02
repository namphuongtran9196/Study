/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;




/**
 *
 * @author Farley Tran
 */
public class MyFilmDatabase
{
    
    private String path;
    private HashMap<Integer,FilmDatabase> filmDatabase;
    public MyFilmDatabase()
    {
        path = System.getProperty("user.dir");
        path = path.replace('\\', '/');
        path+= "/build/classes/data/filmDatabase/filmData.dat";
        try
        {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            filmDatabase = (HashMap)ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex)
        {   
            FilmDatabase.setCount(0);
            filmDatabase = new HashMap<Integer,FilmDatabase>();
            filmDatabase.put(-1,new FilmDatabase(null,null, false, true, null, 
                null, null , null, null, 0, null, null));
        } catch (IOException ex)
        {   
            FilmDatabase.setCount(0);
            filmDatabase = new HashMap<Integer,FilmDatabase>();
            filmDatabase.put(-1,new FilmDatabase(null,null, false, true, null, 
                null, null , null, null, 0, null, null));
        } catch (ClassNotFoundException ex)
        {   
            FilmDatabase.setCount(0);
            filmDatabase = new HashMap<Integer,FilmDatabase>();
            filmDatabase.put(-1,new FilmDatabase(null,null, false, true, null, 
                null, null , null, null, 0, null, null));
        }
        FilmDatabase.setCount(filmDatabase.get(-1).getFilmID());
    }
    public FilmDatabase getRandomMovie()
    {
        if(filmDatabase.size() == 1)
        {
            return filmDatabase.get(-1);
        }
        Random rand = new Random();
        Object[] randomFilm = filmDatabase.keySet().toArray();
        randomFilm[0] = randomFilm[1];
        int indexRandom = rand.nextInt(randomFilm.length);
        return filmDatabase.get((int)randomFilm[indexRandom]);
    }
    public FilmDatabase getMovie(int filmID)
    {
        return filmDatabase.get(filmID);
    }
    public void addMovie(FilmDatabase fd)
    {
        filmDatabase.put(fd.getFilmID(),fd);
        FilmDatabase defaultFilm = filmDatabase.get(-1);
        defaultFilm.setFilmID(FilmDatabase.getCount());
        filmDatabase.replace(-1, defaultFilm);
        saveData();
    }
    public void removeMovie(int filmID)
    {
        
        filmDatabase.remove(filmID);
        saveData();
    }
    public void changeMovieInfo(FilmDatabase fd)
    {
        filmDatabase.replace(fd.getFilmID(), fd);
        saveData();
    }
    public void saveData()
    {
        File f = new File(path);
        if (f.exists())
        {
            f.delete();
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(filmDatabase);
            oos.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println("Can not save");
        } catch (IOException ex)
        {
            System.out.println("Can not save");
        }
    }
    public ArrayList<FilmDatabase> filterListFilm(Filter f)
    {
        ArrayList<FilmDatabase> fdList = new ArrayList<FilmDatabase>();
        fdList.add(filmDatabase.get(-1));
        for(int i : filmDatabase.keySet())
        {
            if(i == -1)
            {
                continue;
            }
            if(f.satisfy(filmDatabase.get(i)))
            {
                fdList.add(filmDatabase.get(i));
            }
        }
        return fdList;
    }
    public HashMap<Integer,FilmDatabase> getListFilm()
    {
        
        return filmDatabase;
    }
}
