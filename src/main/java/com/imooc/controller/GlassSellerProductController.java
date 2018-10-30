package com.imooc.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.beans.BeanUtils;

import com.imooc.dataobject.WxProductInfo;
import com.imooc.exception.SellException;
import com.imooc.service.impl.WxProductInfoServiceImpl;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.ListPageUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/glassSeller/product")
public class GlassSellerProductController {

	@Autowired
	private WxProductInfoServiceImpl wxProductInfoServiceImpl;

	// 卖家商品维护列表
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		List<WxProductInfo> wxProductInfoPage = wxProductInfoServiceImpl.findList();
		ListPageUtil<WxProductInfo> listToPage = new ListPageUtil<WxProductInfo>(wxProductInfoPage, page, size);
		List<WxProductInfo> list = listToPage.getPagedList();
		map.put("productInfoPage", list);
		map.put("currentPage", page);
		map.put("totalPage", listToPage.getTotalPage());
		map.put("size", size);
		return new ModelAndView("glassProduct/list", map);
	}
	

	// 卖家删除商品
	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam(value = "id") String id,
			Map<String, Object> map) {
		if (!StringUtils.isEmpty(id)) {
			try {
				map.put("url", "/sell/glassSeller/product/list");
				wxProductInfoServiceImpl.deleteById(Integer.parseInt(id));
			}catch(Exception e) {
				map.put("msg", e.getMessage());
				return new ModelAndView("common/error", map);
			}
			map.put("msg", "删除成功");
		}
		return new ModelAndView("common/success", map);
	}

	// 卖家修改 新增商品
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "id", required = false) String id,
			Map<String, Object> map) {
		if (!StringUtils.isEmpty(id)) {
			WxProductInfo info = wxProductInfoServiceImpl.selectById(Integer.valueOf(id));
			map.put("productInfo",info);
		}
		return new ModelAndView("glassProduct/index", map);
	}

	/**
	 * 保存/更新
	 * 
	 * @param form
	 * @param bindingResult
	 * @param map
	 * @return
	 */
	
	@PostMapping("/save")
	public ModelAndView save(@Valid WxProductInfo Info, BindingResult bindingResult, Map<String, Object> map) {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/glassSeller/product/index");
			return new ModelAndView("common/error", map);
		}

		WxProductInfo wxProductInfo = new WxProductInfo();
		try {
//			非空为修改
			if (!StringUtils.isEmpty(Info.getId())) {
				wxProductInfo = wxProductInfoServiceImpl.selectById(Info.getId());
				BeanUtils.copyProperties(Info, wxProductInfo);
				wxProductInfo.setPhonenum("123");
				wxProductInfoServiceImpl.updateById(wxProductInfo);
			} else {
				// 如果productId为空, 说明是新增
				//form.setProductId(KeyUtil.genUniqueKey());
				BeanUtils.copyProperties(Info, wxProductInfo);
				wxProductInfo.setPhonenum("456");
				wxProductInfoServiceImpl.insert(wxProductInfo);
			}
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/glassSeller/product/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/glassSeller/product/list");
		return new ModelAndView("common/success", map);
	}
	
}
