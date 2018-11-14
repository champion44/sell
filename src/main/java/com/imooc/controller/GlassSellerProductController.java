package com.imooc.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.MultiValueMap;
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
@RequestMapping("/glassSeller/product")
@Slf4j
public class GlassSellerProductController {

	@Autowired
	private WxProductInfoServiceImpl wxProductInfoServiceImpl;

	@ResponseBody
	@RequestMapping("/productList")
	public String productListForMini() {
		List<WxProductInfo> wxProductInfoPage = wxProductInfoServiceImpl.findList();
		return JsonUtil.toJson(wxProductInfoPage);
	}
	
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
	public ModelAndView delete(@RequestParam(value = "id") String id, Map<String, Object> map) {
		if (!StringUtils.isEmpty(id)) {
			try {
				map.put("url", "/sell/glassSeller/product/list");
				wxProductInfoServiceImpl.deleteById(new BigInteger(id));
			} catch (Exception e) {
				map.put("msg", e.getMessage());
				return new ModelAndView("common/error", map);
			}
			map.put("msg", "删除成功");
		}
		return new ModelAndView("common/success", map);
	}

	// 卖家修改 新增商品
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
		if (!StringUtils.isEmpty(id)) {
			WxProductInfo info = wxProductInfoServiceImpl.selectById(new BigInteger(id));
//			if(info.getPictureids()!=null&&info.getPictureids()!="") {
////				List<>
//			}
			map.put("productInfo", info);
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
	 * @throws IOException
	 */

	@PostMapping("/save")
	public ModelAndView save(@Valid WxProductInfo Info, BindingResult bindingResult, Map<String, Object> map,
			MultipartFile file[]) throws IOException {
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/glassSeller/product/index");
			return new ModelAndView("common/error", map);
		}
		// String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
		// MultiValueMap<String, MultipartFile> uploadFilePath =
		// multiReq.getMultiFileMap();
		// String files = multiReq.getFile("files").getOriginalFilename();
	/*
		for (MultipartFile f : file) {
			// 图片的名字用毫秒数+图片原来的名字拼接
			System.out.println(f.getSize());
			System.out.println(f.getBytes());
			String imgName = System.currentTimeMillis() +f.getOriginalFilename();
			log.info(imgName);
			log.info(f.getInputStream().toString());
		}
		*/
		WxProductInfo wxProductInfo = new WxProductInfo();
		BeanUtils.copyProperties(Info, wxProductInfo);
		try {
			// 非空为修改
			if (!StringUtils.isEmpty(Info.getId())) {
				wxProductInfo = wxProductInfoServiceImpl.selectById(Info.getId());
				BeanUtils.copyProperties(Info, wxProductInfo);
				wxProductInfo.setPhonenum("123修改");
				wxProductInfoServiceImpl.updateByObject(wxProductInfo);
				// wxProductInfoServiceImpl.save(wxProductInfo);
			} else {
				// 如果productId为空, 说明是新增
				BeanUtils.copyProperties(Info, wxProductInfo);
				wxProductInfo.setPhonenum("456新增");
				wxProductInfoServiceImpl.insert(wxProductInfo);
			}
			// wxProductInfoServiceImpl.save(wxProductInfo);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/glassSeller/product/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/glassSeller/product/list");
		return new ModelAndView("common/success", map);
	}

}
