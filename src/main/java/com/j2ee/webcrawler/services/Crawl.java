/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2ee.webcrawler.services;

import com.j2ee.webcrawler.models.Image;
import com.j2ee.webcrawler.models.Product;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author An Le
 */
public class Crawl {
    public static List<Product> crawlProduct(String keyword, int store_in_day) {
        List<Product> list_products_lazada = Crawl.crawlProductLazada(keyword, store_in_day);
        List<Product> list_products_sendo = Crawl.crawlProductSendo(keyword, store_in_day);
        
        list_products_lazada.addAll(list_products_sendo);
        
        return list_products_lazada;
    }
    
    public static List<Product> crawlProductSendo(String keyword, int store_in_day) {
        List<Product> list_products = new ArrayList<>();
        
        try {
            //sendo
            String sendoUrl = "https://www.sendo.vn/tim-kiem?q=" + URLEncoder.encode(keyword, "UTF-8") + "&p=1";
            Document sendoDocument = Jsoup.connect(sendoUrl).get();
            Elements box_products = sendoDocument.select("div.box_product");
            for (Element box_product : box_products) {
                Element name = box_product.select("a.name_product").first();
                Element image = box_product.select("img.imgtodrag").first();
                Element current_price = box_product.select("span.current_price").first();
                Element old_price = box_product.select("span.old_price").first();
                
                if(name==null)
                    continue;
                
                Product product = new Product();
                product.setId("sendo"+box_product.attr("id"));
                product.setName(name.html());
                product.setUrl(name.attr("href"));
                product.setImage(image.attr("src"));
                product.setStoreInDay(store_in_day);
                
                if (current_price!=null) {
                    String price = current_price.html().replaceAll(",", "").replaceAll("\\.", "");
                    price = price.substring(0, price.length()-2);
                    
                    product.setPrice(Integer.parseInt(price));
                    
                }
                
                if(old_price!=null){
                    String originalPrice = old_price.html().replaceAll(",", "").replaceAll("\\.", "");
                    originalPrice = originalPrice.substring(0, originalPrice.length()-2);
                    
                    product.setOriginalPrice(Integer.parseInt(originalPrice));
                } else if(current_price!=null){
                    product.setOriginalPrice(product.getPrice());
                }
                
                list_products.add(product);
            }
        } catch (IOException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list_products;
    }
    
    public static List<Product> crawlProductLazada(String keyword, int store_in_day) {
        JSONParser jsonParser = new JSONParser();
        List<Product> list_products = new ArrayList<>();
        
        try {
            //lazada
            String lazadaUrl = "https://www.lazada.vn/catalog/?q=" + URLEncoder.encode(keyword, "UTF-8") + "&page=1";
            Document lazadaDocument = Jsoup.connect(lazadaUrl).get();
            String lazadaHTML = lazadaDocument.html();
            int dataIndex1 = lazadaHTML.indexOf("window.pageData=")+"window.pageData=".length();
            int dataIndex2 = lazadaHTML.indexOf("</script>", dataIndex1);
            
            String lazadaJsonStr = lazadaHTML.substring(dataIndex1, dataIndex2);
            
            JSONObject lazadaJson = (JSONObject) jsonParser.parse(lazadaJsonStr);
            JSONArray lazada_list_products = (JSONArray) ((JSONObject) lazadaJson.get("mods")).get("listItems");

            for (Object lazada_product : lazada_list_products) {
                JSONObject x = (JSONObject) lazada_product;

                String price = ((String) x.get("priceShow")).replaceAll(",", "").replaceAll("\\.", "");
                price = price.substring(0, price.length() - 2);

                String originalPrice = (String) x.get("originalPrice");
                if (originalPrice == null) {
                    originalPrice = price;
                } else {
                    originalPrice = originalPrice.substring(0, originalPrice.indexOf("."));
                }

                Product product = new Product();
                product.setId("lazada" + x.get("nid"));
                product.setName(x.get("name").toString());
                product.setImage(x.get("image").toString());
                product.setPrice(Integer.parseInt(price));
                product.setOriginalPrice(Integer.parseInt(originalPrice));
                product.setUrl("https:" + x.get("productUrl").toString());
                product.setRatingScore(Float.parseFloat(x.get("ratingScore").toString()));
                product.setStoreInDay(store_in_day);
                list_products.add(product);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list_products;
    }
    
    public static List<Image> crawlImageLazada(String keyword, int store_in_day) {
        JSONParser jsonParser = new JSONParser();
        List<Image> list_images = new ArrayList<>();
        
        try {
            //lazada
            String lazadaUrl = "https://www.lazada.vn/catalog/?q=" + URLEncoder.encode(keyword, "UTF-8");
            Document lazadaDocument = Jsoup.connect(lazadaUrl).get();
            String lazadaHTML = lazadaDocument.html();
            int dataIndex1 = lazadaHTML.indexOf("window.pageData=")+"window.pageData=".length();
            int dataIndex2 = lazadaHTML.indexOf("</script>", dataIndex1);
            
            String lazadaJsonStr = lazadaHTML.substring(dataIndex1, dataIndex2);
            
            JSONObject lazadaJson = (JSONObject) jsonParser.parse(lazadaJsonStr);
            JSONArray lazada_list_images = (JSONArray) ((JSONObject) lazadaJson.get("mods")).get("listItems");

            for (Object lazada_product : lazada_list_images) {
                JSONObject x = (JSONObject) lazada_product;
                
                x.get("image").toString();
                String url = x.get("productUrl").toString();
                if (url.indexOf("//")==0) {
                    url = "https:" + url;
                }
                
                Document document = Jsoup.connect(url).get();
                
                for (Element e : document.select("img")) {
                    String imageUrl = e.attr("src");
                    if (imageUrl.contains(".jpg")) {
                        imageUrl = imageUrl.replaceFirst("120x120q", "720x720q");
                        
                        if (imageUrl.indexOf("//")==0) {
                            imageUrl = "https:"+imageUrl;
                        }
                        
                        Image image = new Image();
                        image.setUrl(imageUrl);
                        image.setName(e.attr("alt"));
                        image.setStoreInDay(store_in_day);
                        list_images.add(image);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list_images;
    }
}
