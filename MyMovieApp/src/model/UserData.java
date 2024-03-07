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
import java.util.HashMap;

/**
 *
 * @author Farley Tran
 */
public class UserData
{
    private String path;
    private String[] user;
    public String getInfo1()
    {
        return user[0];
    }

    public void setInfo1(String info1)
    {
        this.user[0]= info1;
    }

    public String getInfo2()
    {
        return user[1];
    }

    public void setInfo2(String info2)
    {
        this.user[1] = info2;
    }

    public String getInfo3()
    {
        return user[2];
    }

    public void setInfo3(String info3)
    {
        this.user[2] = info3;
    }

    public String getUsername()
    {
        return user[3];
    }

    public void setUsername(String username)
    {
        this.user[3] = username;
    }

    public String getPassword()
    {
        return user[4];
    }

    public void setPassword(String password)
    {
        this.user[4] = password;
    }
    public UserData()
    {
        path = System.getProperty("user.dir");
        path = path.replace('\\', '/');
        path += "/build/classes/data/filmDatabase/userData.dat";
        try
        {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (String[])ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex)
        {   
            user = new String[]{"Name: Trần Phương Nam","Email: namphuongtran9196@gmail.com","Phone Number: 0946653380","Farley","123456"};
        } catch (IOException ex)
        {   
            user = new String[]{"Name: Trần Phương Nam","Email: namphuongtran9196@gmail.com","Phone Number: 0946653380","Farley","123456"};
        } catch (ClassNotFoundException ex)
        {   
            user = new String[]{"Name: Trần Phương Nam","Email: namphuongtran9196@gmail.com","Phone Number: 0946653380","Farley","123456"};
        }
    }
    public void updateUserData()
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
            oos.writeObject(user);
            oos.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println("Can not save");
        } catch (IOException ex)
        {
            System.out.println("Can not save");
        }
    }
}
