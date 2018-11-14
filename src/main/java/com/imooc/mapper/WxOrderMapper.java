package com.imooc.mapper;

import com.imooc.dataobject.WxOrder;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WxOrderMapper {
    int deleteByPrimaryKey(String id);

    @Insert(" insert into wx_order (id, storephone, custname, \n" + 
    		"      custphone, custaddr, productid, \n" + 
    		"      orderfee, buyfee, mailfee, \n" + 
    		"      totalfee, gettype, payflag, \n" + 
    		"      remake)\n" + 
    		"    values (#{id,jdbcType=VARCHAR}, #{storephone,jdbcType=VARCHAR}, #{custname,jdbcType=VARCHAR}, \n" + 
    		"      #{custphone,jdbcType=VARCHAR}, #{custaddr,jdbcType=VARCHAR}, #{productid,jdbcType=INTEGER}, \n" + 
    		"      #{orderfee,jdbcType=DOUBLE}, #{buyfee,jdbcType=DOUBLE}, #{mailfee,jdbcType=DOUBLE}, \n" + 
    		"      #{totalfee,jdbcType=DOUBLE}, #{gettype,jdbcType=INTEGER}, #{payflag,jdbcType=INTEGER}, \n" + 
    		"      #{remake,jdbcType=VARCHAR})")
    int insert(WxOrder record);

    int insertSelective(WxOrder record);

    @Select("select *" +
    		"    from wx_order\n" + 
    		"    where id = #{id,jdbcType=VARCHAR}")
    WxOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxOrder record);

    @Update(" update wx_order\n" + 
    		"    set storephone = #{storephone,jdbcType=VARCHAR},\n" + 
    		"      custname = #{custname,jdbcType=VARCHAR},\n" + 
    		"      custphone = #{custphone,jdbcType=VARCHAR},\n" + 
    		"      custaddr = #{custaddr,jdbcType=VARCHAR},\n" + 
    		"      productid = #{productid,jdbcType=INTEGER},\n" + 
    		"      orderfee = #{orderfee,jdbcType=DOUBLE},\n" + 
    		"      buyfee = #{buyfee,jdbcType=DOUBLE},\n" + 
    		"      mailfee = #{mailfee,jdbcType=DOUBLE},\n" + 
    		"      totalfee = #{totalfee,jdbcType=DOUBLE},\n" + 
    		"      gettype = #{gettype,jdbcType=INTEGER},\n" + 
    		"      payflag = #{payflag,jdbcType=INTEGER},\n" + 
    		"      remake = #{remake,jdbcType=VARCHAR}\n" + 
    		"    where id = #{id,jdbcType=VARCHAR}")
    int updateByPrimaryKey(WxOrder record);
}