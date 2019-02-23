<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
        <title>Web Crawler</title>
        <link rel="stylesheet" href="${contextPath}/public/css/bootstrap-4.1.0/bootstrap.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/fontawesome-free-5.0.10/css/fontawesome-all.min.css" />
        <link rel="stylesheet" href="${contextPath}/public/css/site.css" />
        <script src="${contextPath}/public/js/jquery-3.3.1.min.js"></script>
        <script src="${contextPath}/public/js/popper-1.14.0.min.js"></script>
        <script src="${contextPath}/public/js/bootstrap-4.1.0/bootstrap.min.js"></script>
        <script src="${contextPath}/public/js/main.js"></script>
    </head>

    <body>
        <div class="container">
            <div class="logo-container">
                <img class="logo" src="${contextPath}/public/images/logo.png" width="200" height="150" alt="Google" id="logo"/>
            </div>
            <div class="row">
                <div class="col-md-6 search-form">
                    <form action="${contextPath}/search">
                        <div class="input-group">
                            <input type="text" class="form-control" name="s" placeholder="Keyword" autocomplete="off" />
                            <span class="input-group-append">
                                <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
