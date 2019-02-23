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
public class ProductMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        product.setOriginalPrice(rs.getInt("originalPrice"));
        product.setUrl(rs.getString("url"));
        product.setRatingScore(rs.getFloat("ratingScore"));
        product.setImage(rs.getString("image"));
        return product;
    }
    
}
