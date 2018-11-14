<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<style type="text/css">   
    .float{    
        float:left;    
        width : 200px;    
        height: 200px;    
        overflow: hidden;    
        border: 1px solid #CCCCCC;    
        border-radius: 10px;    
        padding: 5px;    
        margin: 5px;    
    }    
    img{    
        position: relative;    
    }    
    .result{    
        width: 200px;    
        height: 200px;    
        text-align: center;    
        box-sizing: border-box;    
    }   
  
  
    #file_input{  
        display: none;  
    }  
  
  
    .delete{  
        width: 200px;  
        height:200px;  
        position: absolute;  
        text-align: center;  
        line-height: 200px;  
        z-index: 10;  
        font-size: 30px;  
        background-color: rgba(255,255,255,0.8);  
        color: #777;  
        opacity: 0;  
        transition-duration: :0.7s;  
        -webkit-transition-duration: 0.7s;   
    }  
  
  
    .delete:hover{  
        cursor: pointer;  
        opacity: 1;  
    }  
  
  
        
</style>    
    
    
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script> 

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" enctype="multipart/form-data" action="/sell/glassSeller/product/save">
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
                              <div class="form-group imgs">    
                <label>请选择一个图像文件：</label>  
                <label id="select">(重新)选择图片</label>  
                <label id="add">(追加)图片</label>  
                <input type="file" id="file_input" name="file" title="请选择图片" multiple/>    
            </div>    
                        <div class="form-group" style="clear:both">
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
<script type="text/javascript">    
    
window.onload = function(){    
    var input = document.getElementById("file_input");    
    var result;    
    var dataArr = []; // 储存所选图片的结果(文件名和base64数据)  
    var fd;  //FormData方式发送请求    
    var oSelect = document.getElementById("select");  
    var oAdd = document.getElementById("add");  
    var oInput = document.getElementById("file_input");  
     
    if(typeof FileReader==='undefined'){    
        alert("抱歉，你的浏览器不支持 FileReader");    
        input.setAttribute('disabled','disabled');    
    }else{    
        input.addEventListener('change',readFile,false);    
    }　　　　　//handler    
    
    function readFile(){   
        fd = new FormData();    
        var iLen = this.files.length;  
        var index = 0;  
        for(var i=0;i<iLen;i++){  
            if (!input['files'][i].name.match(/.jpg|.gif|.png|.jpeg|.bmp/i)){　　//判断上传文件格式    
                alert("含有格式不正确的上传项目，请重新选择");    
            }  else{
            var reader = new FileReader();  
            reader.index = i;    
            fd.append(i,this.files[i]);  
            reader.readAsDataURL(this.files[i]);  //转成base64    
            reader.fileName = this.files[i].name;  
  
  
            reader.onload = function(e){   
                var imgMsg = {    
                    name : this.fileName,//获取文件名    
                    base64 : this.result   //reader.readAsDataURL方法执行完后，base64数据储存在reader.result里    
                }   
                dataArr.push(imgMsg);    
                result = '<div class="delete">delete</div><div class="result"><img src="'+this.result+'" alt=""/></div>';    
                var div = document.createElement('div');  
                div.innerHTML = result;    
                div['className'] = 'float form-group';  
                div['index'] = index;    
              //  document.getElementsByTagName('body')[0].appendChild(div);  　　//插入dom树   
              $(".imgs").after(div); 
                var img = div.getElementsByTagName('img')[0];  
                img.onload = function(){    
                    var nowHeight = ReSizePic(this); //设置图片大小    
                    this.parentNode.style.display = 'block';    
                    var oParent = this.parentNode;    
                    if(nowHeight){    
                        oParent.style.paddingTop = (oParent.offsetHeight - nowHeight)/2 + 'px';    
                    }    
                }   
  
  
                div.onclick = function(){  
                    this.remove();                  // 在页面中删除该图片元素  
                    delete dataArr[this.index];  // 删除dataArr对应的数据  
                      
                }  
                index++;  
            }  
            }  
        }    
    }    
        
        

  
  
    oSelect.onclick=function(){   
        oInput.value = "";   // 先将oInput值清空，否则选择图片与上次相同时change事件不会触发  
        //清空已选图片  
        $('.float').remove();  
        dataArr = [];   
        index = 0;          
        oInput.click();   
    }   
  
  
    oAdd.onclick=function(){  
        oInput.value = "";   // 先将oInput值清空，否则选择图片与上次相同时change事件不会触发  
        oInput.click();   
    }   
  
  
 
}    
/*    
 用ajax发送fd参数时要告诉jQuery不要去处理发送的数据，    
 不要去设置Content-Type请求头才可以发送成功，否则会报“Illegal invocation”的错误，    
 也就是非法调用，所以要加上“processData: false,contentType: false,”    
 * */    
    
                
function ReSizePic(ThisPic) {    
    var RePicWidth = 200; //这里修改为您想显示的宽度值    
    
    var TrueWidth = ThisPic.width; //图片实际宽度    
    var TrueHeight = ThisPic.height; //图片实际高度    
        
    if(TrueWidth>TrueHeight){    
        //宽大于高    
        var reWidth = RePicWidth;    
        ThisPic.width = reWidth;    
        //垂直居中    
        var nowHeight = TrueHeight * (reWidth/TrueWidth);    
        return nowHeight;  //将图片修改后的高度返回，供垂直居中用    
    }else{    
        //宽小于高    
        var reHeight = RePicWidth;    
        ThisPic.height = reHeight;    
    }    
}    
</script> 
</body>
</html>