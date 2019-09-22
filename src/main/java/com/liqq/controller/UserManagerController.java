package com.liqq.controller;
/**
 * 管理用户
 * @author Administrator
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.liqq.annotation.LogAnnotation;
import com.liqq.common.Code;
import com.liqq.common.ReturnData;
import com.liqq.model.SysUser;
import com.liqq.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "管理用户")
@RestController
@RequestMapping("userManager")
public class UserManagerController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 查询用户列表，分页
	 * 
	 * @param sysUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("userPage")
	@LogAnnotation(module = "用户列表")
	@ApiOperation("查询用户列表，分页")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "页数", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query") })
	public ReturnData userPage(SysUser sysUser, Integer pageNum, Integer pageSize) {
		PageInfo<SysUser> selectPage = sysUserService.selectPage(sysUser, pageNum, pageSize);
		return new ReturnData(Code.OK, selectPage);
	}

	// 查询角色列表

	// 查询资源列表

	// 查询指定用户的角色列表

	// 查询指定角色下的资源列表

	// ======================================//

	// 添加用户

	// 添加角色

	// 添加资源

	// 绑定用户角色，先删除所有已存的，然后再插入所有最新的

	// 绑定角色资源，先删除所有已存的，然后再插入所有最新的

	// 禁用用户，

	// 删除资源

}
