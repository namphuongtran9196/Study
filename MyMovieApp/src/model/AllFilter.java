/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Farley Tran
 */
public class AllFilter implements Filter
{
    private ArrayList<Filter> allFilter;
    public AllFilter()
    {
        allFilter = new ArrayList<Filter>();
    }
    public void addFilter(Filter f)
    {
        allFilter.add(f);
    }
    public boolean satisfy(FilmDatabase fd)
    {
        if(allFilter.size() == 0)
        {
            return false;
        }
        for(Filter f : allFilter)
        {
            if(!f.satisfy(fd))
            {
                return false;
            }
        }
        return true;
    }
}
