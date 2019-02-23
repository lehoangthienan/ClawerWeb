<%-- 
    Document   : search
    Created on : Apr 12, 2018, 1:58:06 AM
    Author     : An Le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
        <title>Search results for ${keyword} - WebCrawler</title>
        
        <link rel="stylesheet" href="${contextPath}/public/css/bootstrap-4.1.0/bootstrap.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/fontawesome-free-5.0.10/css/fontawesome-all.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/site.css" />
    </head>
    <body class="search">
        <div class="header-container">
            <div class="search-container">
                <div class="logo-container">
                    <a href="${contextPath}">
                        <img class="logo" src="${contextPath}/public/images/logo.png" width="70" height="40" alt="Google" id="logo"/>
                    </a>
                </div>
                <div class="search-form">
                    <form action="${contextPath}/search">
                        <div class="input-group">
                            <input type="text" class="form-control" name="s" value="${keyword}" autocomplete="off" />
                            <span class="input-group-append">
                                <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tab-container">
                <ul>
                    <li class="selected"><a>Web</a></li>
                    <li><a href="${contextPath}/search/images?s=${keyword}">Images</a></li>
                </ul>
            </div>
        </div>
        <div id="resultStats">Tìm thấy ${list_products.size()} kết quả (${crawlTime/1000} giây)</div>
        
        <div class="my-container">
            <div class="order">
                <div class="form-group">
                    <label for="formControlOrder">Sắp xếp theo</label>
                    <select class="form-control" id="formControlOrder" name="order">
                        <option value="1" ${param.order.equals("1") ? "selected" : ""}>Giá từ thấp lên cao</option>
                        <option value="2" ${param.order.equals("2") ? "selected" : ""}>Giá từ cao xuống thấp</option>
                        <option value="3" ${param.order.equals("3") ? "selected" : ""}>Nhiều sao</option>
                        <option value="4" ${param.order.equals("4") ? "selected" : ""}>Ít sao</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="card filter">
                        <div>Đánh giá</div>
                        <div class="rating" data-start="5">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 100%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating" data-start="4">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 80%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating" data-start="3">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 60%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating" data-start="2">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 40%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating" data-start="1">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 20%;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        <div class="rating" data-start="0">
                            <span class="rating-content">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span style="width: 0;">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </span>
                            </span>
                        </div>
                        
                        <div>Giá</div>
                        <div class="form-group row">
                            <div class="col-md-5">
                                <input id="inputMinPrice" type="number" value="${param.minPrice}" class="form-control" placeholder="Tối thiểu"/>
                            </div>
                            <div class="col-md-5">
                                <input id="inputMaxPrice" type="number" value="${param.maxPrice}" class="form-control" placeholder="Tối đa"/>
                            </div>
                            <div class="col-md-2">
                                <button id="btnChangeFilterPrice" type="button" class="btn btn-primary">Tìm</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="product-container">
                        <c:forEach var="product" items="${list_products}">
                            <div class="box-product" id="${product.getId()}">
                                <a class="card" href="${product.getUrl()}" target="_blank">
                                    <div class="product-image">
                                        <img src="${product.getImage()}" alt="${product.getName()}" />
                                    </div>
                                    <div class="product-detail">
                                        <div class="product-name">${product.getName()}</div>
                                        <div class="product-price">${product.getPriceDisplay()}</div>
                                        <div class="rating">
                                            <span class="rating-content">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <span style="width: ${product.getRatingScorePercent()}%;">
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                    <i class="fa fa-star"></i>
                                                </span>
                                            </span>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="loading-container" style="display: none;">
                        <img src="${contextPath}/public/images/loading.gif"/>
                    </div>
                    
                    <div class="text-center">
                        <button id="btnLoadMoreWeb" type="button" class="btn btn-primary">Load more...</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var num_items = ${list_products.size()};
            var num_items_per_page = ${list_products.size()};
        </script>
                                
        <script src="${contextPath}/public/js/jquery-3.3.1.min.js"></script>
        <script src="${contextPath}/public/js/popper-1.14.0.min.js"></script>
        <script src="${contextPath}/public/js/bootstrap-4.1.0/bootstrap.min.js"></script>
        <script src="${contextPath}/public/js/main.js"></script>
    </body>
</html>
