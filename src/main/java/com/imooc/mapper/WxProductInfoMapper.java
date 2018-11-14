package com.imooc.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.dataobject.WxProductInfo;

@Mapper
public interface WxProductInfoMapper {
	@Delete("delete from wx_product_info\n" + 
			"    where id = #{id,jdbcType=INTEGER}")
	int deleteByPrimaryKey(BigInteger id);

	@Insert(" insert into wx_product_info (id, storeId, phoneNum, \n" + 
			"      productName, stars, brand, \n" + 
			"      fullPrice, salePrice, deposit, \n" + 
			"      productInfo, indexPic, pic1, \n" + 
			"      pic2, pic3, pic4, pic5, \n" + 
			"      remark1, remark2)\n" + 
			"    values (#{id,jdbcType=INTEGER}, #{storeid,jdbcType=VARCHAR}, #{phonenum,jdbcType=VARCHAR}, \n" + 
			"      #{productname,jdbcType=VARCHAR}, #{stars,jdbcType=VARCHAR}, #{brand,jdbcType=VARCHAR}, \n" + 
			"      #{fullprice,jdbcType=VARCHAR}, #{saleprice,jdbcType=VARCHAR}, #{deposit,jdbcType=VARCHAR}, \n" + 
			"      #{productinfo,jdbcType=VARCHAR}, #{indexpic,jdbcType=VARCHAR}, #{pic1,jdbcType=VARCHAR}, \n" + 
			"      #{pic2,jdbcType=VARCHAR}, #{pic3,jdbcType=VARCHAR}, #{pic4,jdbcType=VARCHAR}, #{pic5,jdbcType=VARCHAR}, \n" + 
			"      #{remark1,jdbcType=VARCHAR}, #{remark2,jdbcType=VARCHAR})")
	int insert(WxProductInfo record);

	int insertSelective(WxProductInfo record);

	@Select("select * from wx_product_info where id = #{id}")
	WxProductInfo selectByPrimaryKey(BigInteger id);

	int updateByPrimaryKeySelective(WxProductInfo record);

	@Update("update wx_product_info\n" + 
			"    set storeId = #{storeid,jdbcType=VARCHAR},\n" + 
			"      phoneNum = #{phonenum,jdbcType=VARCHAR},\n" + 
			"      productName = #{productname,jdbcType=VARCHAR},\n" + 
			"      stars = #{stars,jdbcType=VARCHAR},\n" + 
			"      brand = #{brand,jdbcType=VARCHAR},\n" + 
			"      fullPrice = #{fullprice,jdbcType=VARCHAR},\n" + 
			"      salePrice = #{saleprice,jdbcType=VARCHAR},\n" + 
			"      deposit = #{deposit,jdbcType=VARCHAR},\n" + 
			"      productInfo = #{productinfo,jdbcType=VARCHAR},\n" + 
			"      indexPic = #{indexpic,jdbcType=VARCHAR},\n" + 
			"      pic1 = #{pic1,jdbcType=VARCHAR},\n" + 
			"      pic2 = #{pic2,jdbcType=VARCHAR},\n" + 
			"      pic3 = #{pic3,jdbcType=VARCHAR},\n" + 
			"      pic4 = #{pic4,jdbcType=VARCHAR},\n" + 
			"      pic5 = #{pic5,jdbcType=VARCHAR},\n" + 
			"      remark1 = #{remark1,jdbcType=VARCHAR},\n" + 
			"      remark2 = #{remark2,jdbcType=VARCHAR}\n" + 
			"    where id = #{id,jdbcType=INTEGER}")
	int updateByPrimaryKey(WxProductInfo record);

	@Select("select * from wx_product_info")
	List<WxProductInfo> findList();
}