package com.yc.service;

import java.util.List;

import com.yc.entity.user.DepartAndPositions;

public interface IDepartAndPositionsService extends IGenericService<DepartAndPositions> {

	/***
	 * 删除部门职位的联合表
	 * @param id 联合表ID
	 * @return
	 */
	boolean deleteDepAndPosByID(Integer id);

	/***
	 * 通过部门ID查找联合表，用于显示部门下所有职位
	 * @param departmentID 部门ID
	 * @return
	 */
	List<DepartAndPositions> findDepAndPosByDep(Integer departmentID);

	/***
	 * 通过部门ID、职位ID查找联合表
	 * @param depID 部门ID
	 * @param posID 职位ID
	 * @return
	 */
	DepartAndPositions getAllByDepAndPos(Integer depID, Integer posID);

	/***
	 * 通过部门ID查找联合表，用于显示部门下所有职位，分页
	 * @param depID 部门ID
	 * @return
	 */
	List<DepartAndPositions> getDepByDepID(Integer depID);

}
