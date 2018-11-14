<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                          <th>商品id</th>
                            <th>门店编号</th>
                            <th>手机号码</th>
                            <th>产品名称</th>
                            <th>推荐指数</th>
                             <th>品牌名称</th>
                            <th>图片</th>
                            <th>门市价</th>
                            <th>优惠价</th>
                            <th>定金</th>
                            <th>产品信息</th>
                             <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                          <#list productInfoPage as wxProductInfo>
                        <tr>
                            <td>${wxProductInfo.id}</td>
                            <td>${(wxProductInfo.storeid)!''}</td>
                             <td>${wxProductInfo.phonenum}</td>
                            <td>${wxProductInfo.productname}</td>
                             <td>${wxProductInfo.stars}</td>
                            <td>${wxProductInfo.brand}</td>
                             <td>${wxProductInfo.pictureids!''}</td>
                            <td>${wxProductInfo.fullprice}</td>
                             <td>${wxProductInfo.saleprice}</td>
                            <td>${wxProductInfo.deposit}</td>
                             <td>${wxProductInfo.productinfo}</td>
                              <td>
                                    <a href="/sell/glassSeller/product/delete?id=${wxProductInfo.id}">删除</a>
                            </td>
                             <td>
                                    <a href="/sell/glassSeller/product/index?id=${wxProductInfo.id}">修改</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                
                    <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/glassSeller/order/product?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..totalPage as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/glassSeller/product/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte totalPage>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/glassSeller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>