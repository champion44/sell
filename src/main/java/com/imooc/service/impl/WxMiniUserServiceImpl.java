/**
 * 
 */
package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.WxMiniUser;
import com.imooc.mapper.WxMiniUserMapper;
import com.imooc.service.WxMiniUserService;

/**
* @author champion
* @version 创建时间：Nov 6, 2018 1:42:26 PM
* 类说明
*/
/**
 * @author champion
 *
 */
@Service
public class WxMiniUserServiceImpl implements WxMiniUserService{
	
	@Autowired
	private WxMiniUserMapper wxMiniUserMapper;

	/* (non-Javadoc)
	 * @see com.imooc.service.WxMiniUserService#insert(com.imooc.dataobject.WxMiniUser)
	 */
	@Override
	public Integer insert(WxMiniUser user) {
		return wxMiniUserMapper.insert(user);
	}

}
