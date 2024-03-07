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
public class TrueFilter implements Filter
{
    public TrueFilter()
    {
        
    }
    public boolean satisfy(FilmDatabase fd)
    {
        return true;
    }
    
}
