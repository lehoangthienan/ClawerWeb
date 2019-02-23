/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author An Le
 */
public class ImageMapper implements RowMapper<Image>{

    @Override
    public Image mapRow(ResultSet rs, int i) throws SQLException {
        Image image = new Image();
        image.setId(rs.getString("id"));
        image.setName(rs.getString("name"));
        image.setUrl(rs.getString("url"));
        return image;
    }
    
}
