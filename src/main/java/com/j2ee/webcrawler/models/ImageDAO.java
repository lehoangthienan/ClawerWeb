/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.models;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author An Le
 */
@Repository
@Transactional
public class ImageDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(Image image) {
        String SQL = "insert into Images (name, url, store_in_day) values (?, ?, ?)";
        jdbcTemplate.update(SQL, image.getName(), image.getUrl(), image.getStoreInDay());
    }
    
    public void createIfNotExist(Image image) {
        Image image1 = this.getImageByUrl(image.getUrl());
        if(image1==null)
            this.create(image);
    }
    
    public void creates(List<Image> images){
        for (Image image : images) {
            this.create(image);
        }
    }
    
    public void createsIfNotExist(List<Image> images){
        for (Image image : images) {
            this.createIfNotExist(image);
        }
    }
    
    public Image getImageByUrl(String url) {
        String SQL = "select * from images where url = ?";
        try{
            Image image = jdbcTemplate.queryForObject(SQL, new Object[]{url}, new ImageMapper());
            return image;
        } catch(DataAccessException e){
            return null;
        }
    }
    public void deleteOldImages() {
        String SQL = "delete from images where datediff(now(), date_add) > store_in_day;";
        jdbcTemplate.update(SQL);
    }
    
    
    public List<Image> searchImages(String keyword, int offset, int limit) {
        String SQL = "select * from images where name like ? collate utf8_unicode_ci limit ? offset ?";
        Object[] params = new Object[]{"%"+keyword+"%", limit, offset};
        
        List<Image> images = jdbcTemplate.query(SQL, params, new ImageMapper());
        return images;
    }
}
