package com.imooc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.dataobject.WxProductInfo;

@Mapper
public interface WxProductInfoMapper {
	@Delete("delete from wx_productInfo\n" + 
			"    where id = #{id,jdbcType=INTEGER}")
	int deleteByPrimaryKey(Integer id);

	@Insert("insert into wx_productInfo (id, storeId, phoneNum, \n" + "      productName, stars, brand, \n"
			+ "      fullPrice, salePrice, deposit, \n" + "      indexPage, productInfo, remark1, \n"
			+ "      remark2) values (#{id,jdbcType=INTEGER}, #{storeid,jdbcType=VARCHAR}, #{phonenum,jdbcType=VARCHAR}, \n"
			+ "      #{productname,jdbcType=VARCHAR}, #{stars,jdbcType=VARCHAR}, #{brand,jdbcType=VARCHAR}, \n"
			+ "      #{fullprice,jdbcType=VARCHAR}, #{saleprice,jdbcType=VARCHAR}, #{deposit,jdbcType=VARCHAR}, \n"
			+ "      #{indexpage,jdbcType=VARCHAR}, #{productinfo,jdbcType=VARCHAR}, #{remark1,jdbcType=VARCHAR}, \n"
			+ "      #{remark2,jdbcType=VARCHAR})")
	int insert(WxProductInfo record);

	int insertSelective(WxProductInfo record);

	@Select("select * from wx_productInfo where id = #{id}")
	WxProductInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WxProductInfo record);

	@Update("update wx_productInfo\n" + 
			"    set storeId = #{storeid,jdbcType=VARCHAR},\n" + 
			"      phoneNum = #{phonenum,jdbcType=VARCHAR},\n" + 
			"      productName = #{productname,jdbcType=VARCHAR},\n" + 
			"      stars = #{stars,jdbcType=VARCHAR},\n" + 
			"      brand = #{brand,jdbcType=VARCHAR},\n" + 
			"      fullPrice = #{fullprice,jdbcType=VARCHAR},\n" + 
			"      salePrice = #{saleprice,jdbcType=VARCHAR},\n" + 
			"      deposit = #{deposit,jdbcType=VARCHAR},\n" + 
			"      indexPage = #{indexpage,jdbcType=VARCHAR},\n" + 
			"      productInfo = #{productinfo,jdbcType=VARCHAR},\n" + 
			"      remark1 = #{remark1,jdbcType=VARCHAR},\n" + 
			"      remark2 = #{remark2,jdbcType=VARCHAR}\n" + 
			"    where id = #{id,jdbcType=INTEGER}")
	int updateByPrimaryKey(WxProductInfo record);

	@Select("select * from wx_productInfo")
	List<WxProductInfo> findList();
}