package org.waddys.xcloud.taskMgmt.serviceImpl.dao.serviceImpl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.common.base.utils.DateUtil;
import org.waddys.xcloud.taskMgmt.serviceImpl.dao.entity.TaskInfoE;
import org.waddys.xcloud.taskMgmt.serviceImpl.dao.repository.TaskInfoRepository;
import org.waddys.xcloud.taskMgmt.serviceImpl.dao.service.TaskInfoDaoService;
import org.waddys.xcloud.usermgmt.service.bo.Role;
import org.waddys.xcloud.usermgmt.service.bo.RoleEnum;
import org.waddys.xcloud.usermgmt.service.bo.User;
import org.waddys.xcloud.vm.dao.entity.VmHostE;

@Component("taskInfoDaoServiceImpl")
@Transactional
public class TaskInfoDaoServiceImpl implements TaskInfoDaoService {
	private static Logger logger = LoggerFactory.getLogger(TaskInfoDaoServiceImpl.class);
	@Autowired
	TaskInfoRepository taskInfoRepository;

	@Override
	public TaskInfoE addTaskInfo(TaskInfoE taskInfoE) {
		return taskInfoRepository.save(taskInfoE);
	}

	@Override
	public TaskInfoE updateTaskInfo(TaskInfoE taskInfoE) {
		return taskInfoRepository.save(taskInfoE);
	}

	@Override
	public Page<TaskInfoE> findAllTaskInfo(Pageable pageable, Map<String, String> otherParam) {
		logger.debug("step into method findAllTaskInfo(),param:" + otherParam);
		Page<TaskInfoE> page = null;
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		String orgId = user.getOrgId();
		Role role = user.getRoles().get(0);
		logger.debug("当前登录用户ID:" + user.getId() + ",用户名：" + user.getUsername() + ",用户角色:" + role.getRoleName()
				+ ",组织管ID：" + orgId);
		String startTime = otherParam.get("startTime");
		String endTime = otherParam.get("endTime");
		String resourceName = otherParam.get("resourceName");
		logger.debug("查询参数startTime" + startTime + ",resourceName：" + resourceName);
		// 运营管理员-查看全部任务
		if (role.getRoleName().equals(RoleEnum.OPERATION_MANAGER.getName())) {
			return taskInfoRepository.findAll(new Specification<TaskInfoE>() {
				@Override
				public Predicate toPredicate(Root<TaskInfoE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					List<Expression<Boolean>> expressions = predicate.getExpressions();
					if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
						try {
							expressions.add(
									cb.between(root.get("createDate"), DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss"),
											DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
						} catch (ParseException e) {
							logger.error(e.getMessage());
						}
					}
					if (StringUtils.isNotBlank(startTime) && !(StringUtils.isNotBlank(endTime))) {
						try {
							expressions.add(cb.greaterThan(root.get("createDate"),
									DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss")));
						} catch (ParseException e) {
							logger.error(e.getMessage());
						}
					}
					if (!(StringUtils.isNotBlank(startTime)) && StringUtils.isNotBlank(endTime)) {
						try {
							expressions.add(cb.lessThan(root.get("createDate"),
									DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
						} catch (ParseException e) {
							logger.error(e.getMessage());
						}
					}
					if (StringUtils.isNotEmpty(resourceName)) {
						expressions.add(cb.like(root.get("resourceName"), "%" + resourceName + "%"));
					}
					query.orderBy(cb.desc(root.get("createDate")));
					return predicate;
				}
			}, pageable);

		}
		// 组织管理员-用orgid关联
		if (role.getRoleName().equals(RoleEnum.ORG_MANAGER.getName())) {

			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDateorg(orgId,
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (StringUtils.isNotBlank(startTime) && !(StringUtils.isNotBlank(endTime)) && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDateorgstartTime(orgId,
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!(StringUtils.isNotBlank(startTime)) && StringUtils.isNotBlank(endTime) && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDateorgendTime(orgId,
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!(StringUtils.isNotBlank(startTime)) && !(StringUtils.isNotBlank(endTime)) && resourceName == null) {
				page = taskInfoRepository.findAllorg(orgId, pageable);
				return page;
			}
			if (!(StringUtils.isNotBlank(startTime)) && !(StringUtils.isNotBlank(endTime)) && resourceName != null) {
				page = taskInfoRepository.findAllByresourceNameorg(orgId, resourceName, pageable);
				return page;
			}
			if (StringUtils.isNotBlank(startTime) && !(StringUtils.isNotBlank(endTime)) && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceNameorgendDate(orgId,
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!(StringUtils.isNotBlank(startTime)) && StringUtils.isNotBlank(endTime) && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceNameorgstartDate(orgId,
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceNameorg(orgId,
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// 组织用户
		if (role.getRoleName().equals(RoleEnum.ORG_USER.getName())) {
			if (startTime != null &&endTime != null && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDate(user.getId(),
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (startTime != null && endTime == null && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDatestartDate(user.getId(),
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (startTime == null && endTime != null && resourceName == null) {
				try {
					page = taskInfoRepository.findAllBycreateDateendDate(user.getId(),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (startTime == null && endTime == null && resourceName != null) {
				page = taskInfoRepository.findAllByresourceName(user.getId(), resourceName, pageable);
				return page;
			}
			if (startTime != null && endTime != null && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceName(user.getId(),
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (startTime == null && endTime != null && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceNameendDate(user.getId(),
							DateUtil.parse(otherParam.get("endTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (startTime != null && endTime == null && resourceName != null) {
				try {
					page = taskInfoRepository.findAllBycreateDateandresourceNamestartDate(user.getId(),
							DateUtil.parse(otherParam.get("startTime"), "yyyy-MM-dd hh:mm:ss"), resourceName, pageable);
					return page;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (startTime == null && resourceName == null) {
				page = taskInfoRepository.findAll(user.getId(), pageable);
				return page;
			}
		}
		return page;

	}

	public Page<TaskInfoE> findAllTaskInfoMy(Pageable pageable, Map<String, String> otherParam) {
		logger.debug("step into method findAllTaskInfo(),param:" + otherParam);
		Page<TaskInfoE> page = null;
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		if(user==null){
			return page;
		}
		String orgId = user.getOrgId();
		Role role = user.getRoles().get(0);
		logger.debug("当前登录用户ID:" + user.getId() + ",用户名：" + user.getUsername() + ",用户角色:" + role.getRoleName()
				+ ",组织管ID：" + orgId);
		String startTime = otherParam.get("startTime");
		String endTime = otherParam.get("endTime");
		String resourceName = otherParam.get("resourceName");
		String status = otherParam.get("status");
		logger.debug("查询参数startTime" + startTime + ",resourceName：" + resourceName);
		// 运营管理员-查看全部任务
		return taskInfoRepository.findAll(new Specification<TaskInfoE>() {
			@Override
			public Predicate toPredicate(Root<TaskInfoE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				// 运营管理员-start
				if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
					try {
						expressions.add(
								cb.between(root.get("createDate"), DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss"),
										DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (StringUtils.isNotBlank(startTime) && !(StringUtils.isNotBlank(endTime))) {
					try {
						expressions.add(cb.greaterThan(root.get("createDate"),
								DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (!(StringUtils.isNotBlank(startTime)) && StringUtils.isNotBlank(endTime)) {
					try {
						expressions.add(
								cb.lessThan(root.get("createDate"), DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (StringUtils.isNotEmpty(resourceName)) {
					expressions.add(cb.like(root.get("resourceName"), "%" + resourceName + "%"));
				}
				if (StringUtils.isNotEmpty(status)) {
					expressions.add(cb.equal(root.get("status"),status));
				}
				/*****************************
				 * 运营管理员-end
				 ****************************/
				/*****************************
				 * 组织管理员-start
				 ****************************/
				if (role.getRoleName().equals(RoleEnum.ORG_MANAGER.getName())) {
					expressions.add(cb.equal(root.get("orgId"),orgId));
				}
				/*****************************
				 * 组织管理员-end
				 ****************************/
				/*****************************
				 * 组织成员员-start
				 *************************/
				if (role.getRoleName().equals(RoleEnum.ORG_USER.getName())) {
					expressions.add(cb.equal(root.get("userId"),user.getId()));
				}
				/*****************************
				 * 组织成员员-end
				 **************************/

				query.orderBy(cb.desc(root.get("createDate")));
				return predicate;
			}
		}, pageable);
	}

	@Override
	public List<TaskInfoE> findByStatus(String status) {
		return taskInfoRepository.findByStatus(status);
	}

	@Override
	public Page<TaskInfoE> findTaskRecent(Pageable pageable, Map<String, String> otherParam) {
		logger.debug("step into method findTaskRecent(),param:" + otherParam);
		Page<TaskInfoE> page = null;
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		String orgId = user.getOrgId();
		Role role = user.getRoles().get(0);
		logger.debug("当前登录用户ID:" + user.getId() + ",用户名：" + user.getUsername() + ",用户角色:" + role.getRoleName()
				+ ",组织管ID：" + orgId);
		String startTime = otherParam.get("startTime");
		String endTime = otherParam.get("endTime");
		String resourceName = otherParam.get("resourceName");
		logger.debug("查询参数startTime" + startTime + ",resourceName：" + resourceName);
		// 运营管理员-查看全部任务
		return taskInfoRepository.findAll(new Specification<TaskInfoE>() {
			@Override
			public Predicate toPredicate(Root<TaskInfoE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();
				// 运营管理员-start
				if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
					try {
						expressions.add(
								cb.between(root.get("createDate"), DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss"),
										DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (StringUtils.isNotBlank(startTime) && !(StringUtils.isNotBlank(endTime))) {
					try {
						expressions.add(cb.greaterThan(root.get("createDate"),
								DateUtil.parse(startTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (!(StringUtils.isNotBlank(startTime)) && StringUtils.isNotBlank(endTime)) {
					try {
						expressions.add(
								cb.lessThan(root.get("createDate"), DateUtil.parse(endTime, "yyyy-MM-dd hh:mm:ss")));
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
				}
				if (StringUtils.isNotEmpty(resourceName)) {
					expressions.add(cb.like(root.get("resourceName"), "%" + resourceName + "%"));
				}
				/*****************************
				 * 运营管理员-end
				 ****************************/
				/*****************************
				 * 组织管理员-start
				 ****************************/
				if (role.getRoleName().equals(RoleEnum.ORG_MANAGER.getName())) {
					expressions.add(cb.equal(root.get("orgId"),orgId));
				}
				/*****************************
				 * 组织管理员-end
				 ****************************/
				/*****************************
				 * 组织成员员-start
				 *************************/
				if (role.getRoleName().equals(RoleEnum.ORG_USER.getName())) {
					expressions.add(cb.equal(root.get("userId"),user.getId()));
				}
				/*****************************
				 * 组织成员员-end
				 **************************/

				query.orderBy(cb.desc(root.get("createDate")));
				return predicate;
			}
		}, pageable);
	}

	@Override
	public TaskInfoE findOne(String taskInfoId) {
		return taskInfoRepository.findOne(taskInfoId);
	}

}
