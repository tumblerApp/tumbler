package com.yc.service;

import java.util.List;

import com.yc.entity.user.Positions;

public interface IPositionService extends IGenericService<Positions> {

	/***
	 * 查询职位表父节点为NUll的所有职位
	 * @return
	 */
	List<Positions> getPositionByParent();

	/***
	 * 通过职位ID删除本职位
	 * @param positionsID 职位ID
	 * @return
	 */
	boolean deleteForTree(Integer positionsID);

}
