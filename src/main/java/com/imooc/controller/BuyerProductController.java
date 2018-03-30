/**
 * 
 */
package com.imooc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dataobject.Stores;
import com.imooc.repository.StoresRepository;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.service.StoresService;
import com.imooc.utils.ResultVOUtil;

/**
 * @author champion
 *
 * @version 2017年12月20日下午3:49:59
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StoresService storesService;

	// 买家商户json返回 只返回delFlag为0的商户
	@RequestMapping("/storeList")
	public ResultVO StoresList() {
		List<Stores> storesList = storesService.findList(0);
		return ResultVOUtil.success(storesList);
	}

	
	@RequestMapping("/allStoreList")
	public ResultVO allStoreList() {
		List<Stores> storesList = storesService.findList();
		return ResultVOUtil.success(storesList);
	}
	
	@RequestMapping("/saveStore")
	public ResultVO save(@RequestParam("discount") Integer discount, @RequestParam("storeName") String storeName,
			@RequestParam("storePic") String storePic, @RequestParam("storeSubtitle") String storeSubtitle,
			@RequestParam("storeAdd") String storeAdd, @RequestParam("storeId") Integer storeId,
			@RequestParam("storeTel") String storeTel, @RequestParam("storeTime") String storeTime,
			@RequestParam(value="delFlag",defaultValue="0") Integer delFlag, @RequestParam("storeLocation") String storeLocation) {
		try {
			Stores stores = storesService.findByStoreId(storeId);
			if (stores == null) {
				stores = new Stores();
				stores.setStoreId(storeId);
				stores.setStoreName(storeName);
				stores.setStoreAdd(storeAdd);
				stores.setStorePic(storePic);
				stores.setStoreSubtitle(storeSubtitle);
				stores.setDiscount(discount);
				stores.setDelFlag(delFlag);
				stores.setStoreCategory("");
				stores.setStoreLocation("");
				stores.setStoreTel(storeTel);
				stores.setStoreTime(storeTime);
				stores.setStoreLocation(storeLocation);
			} else {
				stores.setStoreId(storeId);
				stores.setStoreName(storeName);
				stores.setStoreAdd(storeAdd);
				stores.setStorePic(storePic);
				stores.setStoreSubtitle(storeSubtitle);
				stores.setDiscount(discount);
				stores.setDelFlag(delFlag);
				stores.setStoreTel(storeTel);
				stores.setStoreTime(storeTime);
				stores.setStoreLocation(storeLocation);
			}
			storesService.save(stores);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResultVOUtil().error(1, "发生错误");
		}
		return new ResultVOUtil().success();
	}

	@RequestMapping("/list")
	public ResultVO List() {
		// 查询上架的所有商品
		List<ProductInfo> productInfoList = productService.findUpAll();
		// 查询所有类目
		// List<Integer> categoryTypeList = new ArrayList<>();
		// lambda表达式
		List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

		// 数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(productCategory.getCategoryType());

			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);// 第三层
				}
			}
			productVO.setProductInfoVOList(productInfoVOList);
			productVOList.add(productVO);// 第二层
		}

		return ResultVOUtil.success(productVOList);
	}

}
