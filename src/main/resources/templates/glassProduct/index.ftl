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
                    <form role="form" method="post" action="/sell/glassSeller/product/save">
                        <div class="form-group">
                            <label>产品名称</label>
                            <input name="productname" type="text" class="form-control" value="${(productInfo.productname)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>推荐指数</label>
                            <input name="stars" type="number" class="form-control" value="${(productInfo.stars)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>品牌名称</label>
                            <input name="brand"  type="text" class="form-control" value="${(productInfo.brand)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <input name="indexpage" type="text" class="form-control" value="${(productInfo.indexpage)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>门市价</label>
                            <input name="fullprice" type="number" class="form-control" value="${(productInfo.fullprice)!''}"/>
                        </div>
                          <div class="form-group">
                            <label>优惠价</label>
                            <input name="saleprice" type="number" class="form-control" value="${(productInfo.saleprice)!''}"/>
                        </div>
                          <div class="form-group">
                            <label>定金</label>
                            <input name="deposit" type="number" class="form-control" value="${(productInfo.deposit)!''}"/>
                        </div>
                          <div class="form-group">
                            <label>产品信息</label>
                            <input name="productinfo" type="text" class="form-control" value="${(productInfo.productinfo)!''}"/>
                        </div>
                        <!--隐藏商品id-->
                        <input hidden type="text" name="id" value="${(productInfo.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>