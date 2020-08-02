/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import model.*;

/**
 *
 * @author Farley Tran
 */
public class Home extends javax.swing.JFrame
{
    public Home()
    {
        myFilmDatabase = new MyFilmDatabase();
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        myInitComponents();
    }
    private void myInitComponents()
    {
        setLinkLayer();
    }

    private void setFilmPanel()
    {
        filmPanel1 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel2 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel3 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel4 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel6 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel5 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel7 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel8 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel9 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel10 = new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
    }
    private void refreshBackground()
    {
        background.removeAll();
        setLinkLayer();
        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(filmPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(randomBtn)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(filmPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(filmPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allMovieCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(favoriteCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(blurayCheckBox)
                        .addGap(59, 59, 59)
                        .addComponent(addFilmBtn)
                        .addGap(237, 237, 237)
                        .addComponent(minimizeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitBtn))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(filmPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(filmPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(filmPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(filmPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(previousBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextBtn))
                    .addComponent(filmPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addComponent(filmPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(filmPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nextBtn, previousBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exitBtn, minimizeBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {randomBtn, searchBtn});

        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(exitBtn)
                                .addComponent(minimizeBtn))))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(favoriteCheckBox)
                            .addComponent(addFilmBtn)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchBtn)
                            .addComponent(allMovieCheckBox)
                            .addComponent(blurayCheckBox)
                            .addComponent(randomBtn))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filmPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filmPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(previousBtn)
                    .addComponent(nextBtn))
                .addGap(42, 42, 42))
        );

        backgroundLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {nextBtn, previousBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {randomBtn, searchBtn});

        userForm.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        background.repaint();

    }
    private void setThisVisiable(boolean shv)
    {
        this.setVisible(shv);
    }
    private void setLinkLayer()
    {
        LinkLayer link = new LinkLayer()
        {
            public void setHomeVisiable(boolean sh)
            {
                setThisVisiable(sh);
            }
            public void checkBox(FilmDatabase fd)
            {
                if (favoriteCheckBox.isSelected() || allMovieCheckBox.isSelected() || blurayCheckBox.isSelected())
                {
                    for(int i =0; i<listFilmFilter.size(); i++)
                    {
                        if(listFilmFilter.get(i).getFilmID() == fd.getFilmID())
                        {
                            listFilmFilter.remove(i);
                            break;
                        }
                    }
                    markPoint = 1;
                    showFilterResult();
                } else
                {
                    setFilmPanel();
                    refreshBackground();
                }
            }
          
        };
        filmPanel1.setLinkLayer(link);
        filmPanel2.setLinkLayer(link);
        filmPanel3.setLinkLayer(link);
        filmPanel4.setLinkLayer(link);
        filmPanel5.setLinkLayer(link);
        filmPanel6.setLinkLayer(link);
        filmPanel7.setLinkLayer(link);
        filmPanel8.setLinkLayer(link);
        filmPanel9.setLinkLayer(link);
        filmPanel10.setLinkLayer(link);
    }

    private void addFilm(FilmDatabase fd)
    {
        String image = fd.getMovieLocation() + "image1.png";
        copyFile(fd.getImage1(), image);
        fd.setImage1(image);
        image = fd.getMovieLocation() + "image2.png";
        copyFile(fd.getImage2(), image);
        fd.setImage2(image);
        image = fd.getMovieLocation() + "image3.png";
        copyFile(fd.getImage3(), image);
        fd.setImage3(image);
        image = fd.getMovieLocation() + "background.png";
        copyFile(fd.getBackground(), image);
        fd.setBackground(image);
        myFilmDatabase.addMovie(fd);
    }
    private void copyFile(String src, String des)
    {
        try
        {
            Files.copy(new File(src).toPath(),
                    new File(des).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex)
        {
            System.out.println("Fail to copy file");
        }
    }
    private ImageIcon getImageIcon(String path, int widthImg,
            int heightImg)
    {
        ImageIcon dabIcon = new ImageIcon(path);
        Image dabImage = dabIcon.getImage();
        Image modDabImage = dabImage.getScaledInstance(widthImg,
                heightImg, widthImg);
        return new ImageIcon(modDabImage);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        background = new javax.swing.JDesktopPane()
        {
            protected void paintComponent(Graphics grphcs)
            {
                super.paintComponent(grphcs);
                Image img = getImageIcon("src/data/home/background.png",background.getWidth(), background.getHeight()).getImage();
                grphcs.drawImage(img, 0, 0, null);
            }
        };
        exitBtn = new javax.swing.JButton();
        minimizeBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        previousBtn = new javax.swing.JButton();
        userForm = new gui.UserForm();
        addFilmBtn = new javax.swing.JButton();
        favoriteCheckBox = new javax.swing.JCheckBox();
        searchBtn = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        filmPanel1 = new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel2 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel3 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel4 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel6 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel5 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel7 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel8 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel9 =  new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        filmPanel10 = new gui.FilmPanel(myFilmDatabase.getRandomMovie(),myFilmDatabase);
        allMovieCheckBox = new javax.swing.JCheckBox();
        blurayCheckBox = new javax.swing.JCheckBox();
        randomBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        exitBtn.setBackground(new java.awt.Color(0, 0, 0));
        exitBtn.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(255, 255, 255));
        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitBtnActionPerformed(evt);
            }
        });

        minimizeBtn.setBackground(new java.awt.Color(0, 0, 0));
        minimizeBtn.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        minimizeBtn.setForeground(new java.awt.Color(255, 255, 255));
        minimizeBtn.setText("Minimize");
        minimizeBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                minimizeBtnActionPerformed(evt);
            }
        });

        nextBtn.setBackground(new java.awt.Color(0, 0, 0));
        nextBtn.setVisible(false);
        nextBtn.setFont(new java.awt.Font("Harrington", 1, 14)); // NOI18N
        nextBtn.setForeground(new java.awt.Color(255, 255, 255));
        nextBtn.setText("Next>");
        nextBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nextBtnActionPerformed(evt);
            }
        });

        previousBtn.setBackground(new java.awt.Color(0, 0, 0));
        previousBtn.setVisible(false);
        previousBtn.setFont(new java.awt.Font("Harrington", 1, 14)); // NOI18N
        previousBtn.setForeground(new java.awt.Color(255, 255, 255));
        previousBtn.setText("<Previous");
        previousBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                previousBtnActionPerformed(evt);
            }
        });

        addFilmBtn.setBackground(new java.awt.Color(0, 0, 0));
        addFilmBtn.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        addFilmBtn.setForeground(new java.awt.Color(255, 255, 255));
        addFilmBtn.setText("Add Film");
        addFilmBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addFilmBtnActionPerformed(evt);
            }
        });

        favoriteCheckBox.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        favoriteCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        favoriteCheckBox.setText("Favorite");
        favoriteCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                favoriteCheckBoxActionPerformed(evt);
            }
        });

        searchBtn.setBackground(new java.awt.Color(0, 0, 0));
        searchBtn.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                searchBtnActionPerformed(evt);
            }
        });

        searchTextField.setFont(new java.awt.Font("Harrington", 0, 18)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(0, 0, 0));

        allMovieCheckBox.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        allMovieCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        allMovieCheckBox.setText("All movies");
        allMovieCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                allMovieCheckBoxActionPerformed(evt);
            }
        });

        blurayCheckBox.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        blurayCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        blurayCheckBox.setText("Blu-ray");
        blurayCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                blurayCheckBoxActionPerformed(evt);
            }
        });

        randomBtn.setBackground(new java.awt.Color(0, 0, 0));
        randomBtn.setFont(new java.awt.Font("Harrington", 1, 18)); // NOI18N
        randomBtn.setForeground(new java.awt.Color(255, 255, 255));
        randomBtn.setText("Random");
        randomBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                randomBtnActionPerformed(evt);
            }
        });

        background.setLayer(exitBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(minimizeBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(nextBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(previousBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(userForm, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(addFilmBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(favoriteCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(searchBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(searchTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(filmPanel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(allMovieCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(blurayCheckBox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        background.setLayer(randomBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(filmPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(randomBtn)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allMovieCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(favoriteCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(filmPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filmPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filmPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filmPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(blurayCheckBox)
                        .addGap(59, 59, 59)
                        .addComponent(addFilmBtn)
                        .addGap(237, 237, 237)
                        .addComponent(minimizeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitBtn))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filmPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filmPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filmPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filmPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(filmPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(448, 448, 448)
                .addComponent(previousBtn)
                .addGap(50, 50, 50)
                .addComponent(nextBtn)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nextBtn, previousBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exitBtn, minimizeBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {randomBtn, searchBtn});

        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(exitBtn)
                                .addComponent(minimizeBtn))))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(favoriteCheckBox)
                            .addComponent(addFilmBtn)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchBtn)
                            .addComponent(allMovieCheckBox)
                            .addComponent(blurayCheckBox)
                            .addComponent(randomBtn))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filmPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filmPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filmPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(previousBtn)
                    .addComponent(nextBtn))
                .addGap(42, 42, 42))
        );

        backgroundLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {nextBtn, previousBtn});

        backgroundLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {randomBtn, searchBtn});

        userForm.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exitBtnActionPerformed
    {//GEN-HEADEREND:event_exitBtnActionPerformed
        myFilmDatabase.saveData();
        System.exit(0);
    }//GEN-LAST:event_exitBtnActionPerformed

    private void minimizeBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_minimizeBtnActionPerformed
    {//GEN-HEADEREND:event_minimizeBtnActionPerformed
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_minimizeBtnActionPerformed

    private void previousBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_previousBtnActionPerformed
    {//GEN-HEADEREND:event_previousBtnActionPerformed
        int surplus = (markPoint -1)%10;
        if (surplus == 0)
        {
            surplus =10;
        }
        markPoint = markPoint - surplus - 10;
        if(markPoint < 1)
        {
            markPoint = 1;
        }
        System.out.println("Before showFilter:"+markPoint);
        showFilterResult();
        System.out.println("After show Filter:"+markPoint);
    }//GEN-LAST:event_previousBtnActionPerformed

    private void addFilmBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addFilmBtnActionPerformed
    {//GEN-HEADEREND:event_addFilmBtnActionPerformed
        afd = new AddFilmDialog(this, true);
        afd.setMyFilmDatabase(myFilmDatabase);
        FilmInfo filmInfo = new FilmInfo()
        {
            public void getFilmInfo(FilmDatabase fd)
            {
                addFilm(fd);
                setFilmPanel();
                refreshBackground();
            }
        };
        afd.setFilmInfo(filmInfo);
        afd.setVisible(true);
    }//GEN-LAST:event_addFilmBtnActionPerformed

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nextBtnActionPerformed
    {//GEN-HEADEREND:event_nextBtnActionPerformed
        if(markPoint <  listFilmFilter.size())
        {
            showFilterResult();
        }
        System.out.println(markPoint);
    }//GEN-LAST:event_nextBtnActionPerformed

    private void favoriteCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_favoriteCheckBoxActionPerformed
    {//GEN-HEADEREND:event_favoriteCheckBoxActionPerformed
        if(favoriteCheckBox.isSelected())
        {
            allMovieCheckBox.setSelected(false);
        }
    }//GEN-LAST:event_favoriteCheckBoxActionPerformed
    private void showFilterResult()
    {
        if(listFilmFilter.size() == 1)
        {
            FilmDatabase film = listFilmFilter.get(0);
            filmPanel1 = new FilmPanel(film, myFilmDatabase);
            filmPanel2 = new FilmPanel(film, myFilmDatabase);
            filmPanel3 = new FilmPanel(film, myFilmDatabase);
            filmPanel4 = new FilmPanel(film, myFilmDatabase);
            filmPanel5 = new FilmPanel(film, myFilmDatabase);
            filmPanel6 = new FilmPanel(film, myFilmDatabase);
            filmPanel7 = new FilmPanel(film, myFilmDatabase);
            filmPanel8 = new FilmPanel(film, myFilmDatabase);
            filmPanel9 = new FilmPanel(film, myFilmDatabase);
            filmPanel10 = new FilmPanel(film, myFilmDatabase);
            refreshBackground();
        } else
        {
            FilmDatabase film = listFilmFilter.get(markPoint++);
            filmPanel1 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel2 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel3 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel4 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel5 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel6 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel7 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel8 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel9 = new FilmPanel(film, myFilmDatabase);
            if(markPoint>=listFilmFilter.size())
                markPoint--;
            film = listFilmFilter.get(markPoint++);
            filmPanel10 = new FilmPanel(film, myFilmDatabase);
            System.out.println("markPoint: " +markPoint);
            System.out.println("list film size: " +listFilmFilter.size());
            refreshBackground();
        }      
    }
    private void allMovieCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_allMovieCheckBoxActionPerformed
    {//GEN-HEADEREND:event_allMovieCheckBoxActionPerformed
        if (allMovieCheckBox.isSelected())
        {
            favoriteCheckBox.setSelected(false);
            favoriteCheckBox.setEnabled(false);
            
            blurayCheckBox.setSelected(false);
            blurayCheckBox.setEnabled(false);
        } else
        {
            favoriteCheckBox.setEnabled(true);
            blurayCheckBox.setEnabled(true);
        }
    }//GEN-LAST:event_allMovieCheckBoxActionPerformed

    private void blurayCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_blurayCheckBoxActionPerformed
    {//GEN-HEADEREND:event_blurayCheckBoxActionPerformed
        if(blurayCheckBox.isSelected())
        {

            allMovieCheckBox.setSelected(false);
        }
    }//GEN-LAST:event_blurayCheckBoxActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchBtnActionPerformed
    {//GEN-HEADEREND:event_searchBtnActionPerformed
        markPoint = 1;
        AllFilter af = new AllFilter();
        if(favoriteCheckBox.isSelected())
        {
            FavoriteFilter ff = new FavoriteFilter();
            af.addFilter(ff);
        } else
        {
            NotFavoriteFilter nff = new NotFavoriteFilter();
            af.addFilter(nff);
        }
        if(blurayCheckBox.isSelected())
        {
            BlurayFilter bf = new BlurayFilter();
            af.addFilter(bf);
        } else 
        {
            NotBlurayFilter nbf = new NotBlurayFilter();
            af.addFilter(nbf);
        }
        if(allMovieCheckBox.isSelected())
        {
            af = new AllFilter();
            TrueFilter tf = new TrueFilter();
            af.addFilter(tf);
        }
        listFilmFilter = myFilmDatabase.filterListFilm(af);
        if(searchTextField.getText().equals(""))
        {
            showFilterResult();
        } else
        {
            for(int i =1; i<listFilmFilter.size(); i++)
            {
                if(!listFilmFilter.get(i)
                        .getName().toLowerCase()
                        .contains(searchTextField.getText().toLowerCase()))
                {
                    listFilmFilter.remove(i);
                    i--;
                }
            }
            showFilterResult();
        }
        previousBtn.setVisible(true);
        nextBtn.setVisible(true);
    }//GEN-LAST:event_searchBtnActionPerformed

    private void randomBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_randomBtnActionPerformed
    {//GEN-HEADEREND:event_randomBtnActionPerformed
        setFilmPanel();
        refreshBackground();
        previousBtn.setVisible(false);
        nextBtn.setVisible(false);
    }//GEN-LAST:event_randomBtnActionPerformed
    private int markPoint;
    private AddFilmDialog afd;
    private MyFilmDatabase myFilmDatabase;
    private ArrayList<FilmDatabase> listFilmFilter;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFilmBtn;
    private javax.swing.JCheckBox allMovieCheckBox;
    private javax.swing.JDesktopPane background;
    private javax.swing.JCheckBox blurayCheckBox;
    private javax.swing.JButton exitBtn;
    private javax.swing.JCheckBox favoriteCheckBox;
    private gui.FilmPanel filmPanel1;
    private gui.FilmPanel filmPanel10;
    private gui.FilmPanel filmPanel2;
    private gui.FilmPanel filmPanel3;
    private gui.FilmPanel filmPanel4;
    private gui.FilmPanel filmPanel5;
    private gui.FilmPanel filmPanel6;
    private gui.FilmPanel filmPanel7;
    private gui.FilmPanel filmPanel8;
    private gui.FilmPanel filmPanel9;
    private javax.swing.JButton minimizeBtn;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton previousBtn;
    private javax.swing.JButton randomBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTextField;
    private gui.UserForm userForm;
    // End of variables declaration//GEN-END:variables
}
