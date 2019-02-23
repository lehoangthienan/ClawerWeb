/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.controllers;

import com.j2ee.webcrawler.models.Constant;
import com.j2ee.webcrawler.models.Image;
import com.j2ee.webcrawler.models.ImageDAO;
import com.j2ee.webcrawler.models.Product;
import com.j2ee.webcrawler.models.ProductDAO;
import com.j2ee.webcrawler.services.Crawl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author An Le
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ImageDAO imageDAO;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String searchWeb(
            ModelMap modelMap,
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "deep", defaultValue = "10", required = false) String deep,
            @RequestParam(value = "minPrice", defaultValue = "0", required = false) String minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "-1", required = false) String maxPrice,
            @RequestParam(value = "ratingScore", defaultValue = "-1", required = false) String ratingScore,
            @RequestParam(value = "order", defaultValue = "1", required = false) String order,
            @RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
            @RequestParam(value = "limit", defaultValue = "0", required = false) String limit,
            @RequestParam(value = "store_in_day", defaultValue = "0", required = false) String store_in_day
    )throws UnsupportedEncodingException, IOException{
        long crawlStartTime = System.currentTimeMillis();
        
        //query db
        int iMinPrice = Integer.parseInt(minPrice);
        int iMaxPrice = Integer.parseInt(maxPrice);
        int iRatingScore = Integer.parseInt(ratingScore);
        int iOffset = Integer.parseInt(offset);
        int iLimit = Integer.parseInt(limit);
        int istore_in_day = Integer.parseInt(store_in_day);
        if(iLimit <= 0)
            iLimit = Constant.ITEM_PER_PAGE;
        if(istore_in_day <= 0)
            istore_in_day = Constant.TEMP_STORE;
        
        productDAO.deleteOldProducts();
        List<Product> searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order, iOffset, iLimit);
        
        if(searchProducts.size()<iLimit){
            //craw more
            List<Product> list_products = Crawl.crawlProduct(keyword, istore_in_day);

            //lưu vào db
            productDAO.createsIfNotExist(list_products);
            
            //re-search
            searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order, iOffset, iLimit);
        }
        
        long crawlEndTime = System.currentTimeMillis();
        
        modelMap.put("keyword", keyword);
        modelMap.put("crawlTime", crawlEndTime - crawlStartTime);
        modelMap.put("list_products", searchProducts);
        
        return "search_web";
    }
    
    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchWebAPI(
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "deep", defaultValue = "10", required = false) String deep,
            @RequestParam(value = "minPrice", defaultValue = "0", required = false) String minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "-1", required = false) String maxPrice,
            @RequestParam(value = "ratingScore", defaultValue = "-1", required = false) String ratingScore,
            @RequestParam(value = "order", defaultValue = "1", required = false) String order,
            @RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
            @RequestParam(value = "limit", defaultValue = "0", required = false) String limit,
            @RequestParam(value = "store_in_day", defaultValue = "0", required = false) String store_in_day
    )throws UnsupportedEncodingException, IOException{
        //query db
        int iMinPrice = Integer.parseInt(minPrice);
        int iMaxPrice = Integer.parseInt(maxPrice);
        int iRatingScore = Integer.parseInt(ratingScore);
        int iOffset = Integer.parseInt(offset);
        int iLimit = Integer.parseInt(limit);
        int istore_in_day = Integer.parseInt(store_in_day);
        if(iLimit <= 0)
            iLimit = Constant.ITEM_PER_PAGE;
        if(istore_in_day <= 0)
            istore_in_day = Constant.TEMP_STORE;
        
        productDAO.deleteOldProducts();
        List<Product> searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order, iOffset, iLimit);
        
        if(searchProducts.size()<iLimit){
            //craw more
            List<Product> list_products = Crawl.crawlProduct(keyword, istore_in_day);

            //lưu vào db
            productDAO.createsIfNotExist(list_products);
            
            //re-search
            searchProducts = productDAO.searchProducts(keyword, iMinPrice, iMaxPrice, iRatingScore, order, iOffset, iLimit);
        }
        
        //JSON
        JSONArray jSONArray = new JSONArray();
        for (Product product : searchProducts) {
            jSONArray.add(product.toJSONObject());
        }
        
        return jSONArray.toJSONString();
    }
    
    @RequestMapping(value = "/images", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String searchImage(
            ModelMap modelMap,
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
            @RequestParam(value = "limit", defaultValue = "0", required = false) String limit,
            @RequestParam(value = "store_in_day", defaultValue = "0", required = false) String store_in_day
    )throws UnsupportedEncodingException, IOException{
        long crawlStartTime = System.currentTimeMillis();
        
        int iOffset = Integer.parseInt(offset);
        int iLimit = Integer.parseInt(limit);
        int istore_in_day = Integer.parseInt(store_in_day);
        if(iLimit <= 0)
            iLimit = Constant.ITEM_PER_PAGE;
        if(istore_in_day <= 0)
            istore_in_day = Constant.TEMP_STORE;
        
        imageDAO.deleteOldImages();
        List<Image> searchImages = imageDAO.searchImages(keyword, iOffset, iLimit);
        
        if(searchImages.size()<iLimit){
            //craw more
            List<Image> list_images = Crawl.crawlImageLazada(keyword, istore_in_day);

            //lưu vào db
            imageDAO.createsIfNotExist(list_images);
            
            //re-search
            searchImages = imageDAO.searchImages(keyword, iOffset, iLimit);
        }
        
        long crawlEndTime = System.currentTimeMillis();
        
        modelMap.put("keyword", keyword);
        modelMap.put("crawlTime", crawlEndTime - crawlStartTime);
        modelMap.put("list_images", searchImages);
        
        
        return "search_images";
    }
    
    @RequestMapping(value = "/images/api", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String searchImageAPI(
            @RequestParam(value = "s", defaultValue = " ", required = false) String keyword,
            @RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
            @RequestParam(value = "limit", defaultValue = "0", required = false) String limit,
            @RequestParam(value = "store_in_day", defaultValue = "0", required = false) String store_in_day
    )throws UnsupportedEncodingException, IOException{
        int iOffset = Integer.parseInt(offset);
        int iLimit = Integer.parseInt(limit);
        int istore_in_day = Integer.parseInt(store_in_day);
        if(iLimit <= 0)
            iLimit = Constant.ITEM_PER_PAGE;
        if(istore_in_day <= 0)
            istore_in_day = Constant.TEMP_STORE;
        
        imageDAO.deleteOldImages();
        List<Image> searchImages = imageDAO.searchImages(keyword, iOffset, iLimit);
        
        if(searchImages.size()<iLimit){
            //craw more
            List<Image> list_images = Crawl.crawlImageLazada(keyword, istore_in_day);

            //lưu vào db
            imageDAO.createsIfNotExist(list_images);
            
            //re-search
            searchImages = imageDAO.searchImages(keyword, iOffset, iLimit);
        }
        
        //JSON
        JSONArray jSONArray = new JSONArray();
        for (Image image : searchImages) {
            jSONArray.add(image.toJSONObject());
        }
        
        return jSONArray.toJSONString();
    }
}
