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
public class NotBlurayFilter implements Filter
{
    public boolean satisfy(FilmDatabase fd)
    {
        if (fd.isBluray())
        {
            return false;
        }
        return true;
    }
    
}
