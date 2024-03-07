/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Farley Tran
 */
public class NotFavoriteFilter implements Filter
{
    public NotFavoriteFilter()
    {
        
    }
    public boolean satisfy(FilmDatabase fd)
    {
        if (fd.isFavorite())
        {
            return false;
        }
        return true;
    }
    
}
