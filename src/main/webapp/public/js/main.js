/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$( document ).ready(function() {
    $("#formControlOrder").on("change", (e)=>{
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: $.urlParam("minPrice"),
           maxPrice: $.urlParam("maxPrice"),
           ratingScore: $.urlParam("ratingScore"),
           order: e.target.value,
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    $("#btnChangeFilterPrice").on("click", (e)=>{
        var minPrice = $("#inputMinPrice").val();
        var maxPrice = $("#inputMaxPrice").val();
        
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: minPrice,
           maxPrice: maxPrice,
           ratingScore: $.urlParam("ratingScore"),
           order: $.urlParam("order"),
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    $(".filter .rating").on("click", e=>{
        var ratingScore=$(e.currentTarget).data("start");
        
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: $.urlParam("minPrice"),
           maxPrice: $.urlParam("maxPrice"),
           ratingScore: ratingScore,
           order: $.urlParam("order"),
        });
        
        window.location = window.location.pathname + "?" + httpQuery;
    })
    
    // on click "load more" on web tab
    $("#btnLoadMoreWeb").on("click", (e)=>{
        var httpQuery = $.param({
           s: $.urlParam("s"),
           minPrice: $.urlParam("minPrice"),
           maxPrice: $.urlParam("maxPrice"),
           ratingScore: $.urlParam("ratingScore"),
           order: $.urlParam("order"),
           offset: num_items,
           limit: num_items_per_page,
        });
        
        $("div.loading-container").show();
        $("#btnLoadMoreWeb").prop('disabled', true);
        
        var url = window.location.pathname.replace("search", "search/api") + "?" + httpQuery;
        $.get(url, (data, status)=>{
            $("div.loading-container").hide();
            $("#btnLoadMoreWeb").prop('disabled', false);
            
            if(status==="success"){
                num_items += num_items_per_page;
                
                var product_container = $('.product-container');
                
                var box_product=$(".box-product");
                if(box_product.length<=0)
                    return;
                
                box_product = box_product[0];
                
                data.forEach(product=>{
                    var item_box_product = $(box_product).clone();
                    item_box_product.attr("id", product.id);
                    item_box_product.find("a.card").attr("href", product.url);
                    item_box_product.find(".product-image>img").attr("src", product.image).attr("alt", product.name);
                    item_box_product.find(".product-name").html(product.name);
                    item_box_product.find(".product-price").html(product.priceDisplay);
                    item_box_product.find(".rating-content>span").css({"width": product.ratingScorePercent+"%"});
                    
                    product_container.append(item_box_product);
                })
            }
        });
    })
    
    // on click "load more" on image tab
    $("#btnLoadMoreImage").on("click", (e)=>{
        var httpQuery = $.param({
           s: $.urlParam("s"),
           offset: num_items,
           limit: num_items_per_page,
        });
        
        $("div.loading-container").show();
        $("#btnLoadMoreImage").prop('disabled', true);
        
        var url = window.location.pathname.replace("search/images", "search/images/api") + "?" + httpQuery;
        $.get(url, (data, status)=>{
            $("div.loading-container").hide();
            $("#btnLoadMoreImage").prop('disabled', false);
            
            if(status==="success"){
                num_items += num_items_per_page;
                
                var image_container = $('.image-container');
                
                var image_link=$(".image-link");
                if(image_link.length<=0)
                    return;
                
                image_link = image_link[0];
                
                data.forEach(image=>{
                    var item_image = $(image_link).clone();
                    item_image.attr("href", image.url);
                    item_image.find(".image-image").attr("src", image.url).attr("alt", image.name);
                    image_container.append(item_image);
                })
            }
        });
    })
    
    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        } else {
            results[1] = results[1].replace(/\+/g, '%20');
            return decodeURI(results[1]) || null;
        }
    }
});