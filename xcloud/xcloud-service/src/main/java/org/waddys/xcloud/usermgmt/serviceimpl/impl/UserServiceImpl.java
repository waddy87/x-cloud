package org.waddys.xcloud.usermgmt.serviceimpl.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.usermgmt.service.bo.Role;
import org.waddys.xcloud.usermgmt.service.bo.RoleEnum;
import org.waddys.xcloud.usermgmt.service.bo.User;
import org.waddys.xcloud.usermgmt.service.exception.UserMgmtException;
import org.waddys.xcloud.usermgmt.service.service.UserService;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.RoleE;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.entity.UserE;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.service.RoleDaoService;
import org.waddys.xcloud.usermgmt.serviceimpl.dao.service.UserDaoService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory
            .getLogger(UserServiceImpl.class);
    @Autowired
    private UserDaoService userDaoServiceImpl;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleDaoService roleDaoServiceImpl;
//    @Autowired
//    private SessionDAO sessionDAO;

    @Override
    public User save(User user) throws UserMgmtException {
        passwordHelper.encryptPassword(user);
        UserE userE = new UserE();
        try {
            Mapper mapper = new DozerBeanMapper();
            userE = mapper.map(user, UserE.class);
            userE = userDaoServiceImpl.save(userE);
            user = mapper.map(userE, User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("保存用户失败！");
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws UserMgmtException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUser(String id) throws UserMgmtException {
        User user = new User();
        try {
            UserE userE = userDaoServiceImpl.findUser(id);
            Mapper mapper = new DozerBeanMapper();
            user = mapper.map(userE, User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("查询用户详情失败！");
        }
        return user;
    }

    @Override
    public void delete(String id) throws UserMgmtException {
        try {
            userDaoServiceImpl.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("删除用户失败！");
        }
    }

    @Override
    public void update(User user) throws UserMgmtException {
        UserE userE = new UserE();
        try {
            userE = userDaoServiceImpl.findUser(user.getId());
            if (StringUtils.isNotBlank(user.getTelephone())) {
                userE.setTelephone(user.getTelephone());
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                userE.setEmail(user.getEmail());
            }
            if (StringUtils.isNotBlank(user.getRealname())) {
                userE.setRealname(user.getRealname());
            }
            if (null != user.getLocked()) {
                userE.setLocked(user.getLocked());
            }
            userDaoServiceImpl.save(userE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("更新用户失败！");
        }
    }

    @Override
    public Map<String, Object> findUsers(User user, Integer pageNum,
            Integer pageSize) throws UserMgmtException {
        List<User> userList = new ArrayList<User>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Mapper mapper = new DozerBeanMapper();
            UserE userESearch = mapper.map(user, UserE.class);
            Pageable pageable = new PageRequest(pageNum - 1, pageSize);
            Page<UserE> userListPage = userDaoServiceImpl.findUsers(
                    userESearch, pageable);
            List<UserE> userEList = userListPage.getContent();
            for (UserE userE : userEList) {
                User userTmp = new User();
                userTmp = mapper.map(userE, User.class);
                userList.add(userTmp);
            }
            map.put("total", userListPage.getTotalElements());
            map.put("userList", userList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("分页查询用户信息失败！");
        }

        return map;
    }

    @Override
    public User findByUsername(String username) throws UserMgmtException {
        User user = null;
        try {
            UserE userE = userDaoServiceImpl.findByUsername(username);
            if (userE != null) {
                Mapper mapper = new DozerBeanMapper();
                user = mapper.map(userE, User.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("根据用户名查询用户信息失败！");
        }

        return user;
    }

    @Override
    public void changePassword(String userId, String oldPassword,
            String newPassword) throws UserMgmtException {

        Mapper mapper = new DozerBeanMapper();
        User user = this.findUser(userId);
        Boolean tag = passwordHelper.equalTo(user, oldPassword);

        if (!tag) {
            throw new UserMgmtException("旧密码不正确！");
        } else {
            user.setPassword(newPassword);
            passwordHelper.encryptPassword(user);
            UserE userE = mapper.map(user, UserE.class);
            try {
                userDaoServiceImpl.save(userE);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UserMgmtException("修改密码失败！");
            }
        }

    }

    @Override
    public void resetPassword(String userId, String newPassword)
            throws UserMgmtException {
        try {
            Mapper mapper = new DozerBeanMapper();
            User user = this.findUser(userId);
            user.setPassword(newPassword);
            passwordHelper.encryptPassword(user);
            UserE userE = mapper.map(user, UserE.class);
            userDaoServiceImpl.save(userE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("修改密码失败！");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user, String roleId) throws UserMgmtException {
        List<Role> roleList = new ArrayList<Role>();
        try {
            user = this.save(user);// 创建用户
            RoleE roleE = roleDaoServiceImpl.findRole(roleId);// 查询角色
            Mapper mapper = new DozerBeanMapper();
            Role role = mapper.map(roleE, Role.class);
            roleList.add(role);
            user.setRoles(roleList);
            UserE userE = mapper.map(user, UserE.class);
            userE.setCreateDate(new Date());
            userDaoServiceImpl.save(userE);// 更新用户-将角色更新进来
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("保存用户失败！");
        }
    }

    @Override
    public User findOrgManagerByOrgId(String orgId) throws UserMgmtException {
        User user = null;
        try {
            Mapper mapper = new DozerBeanMapper();
            List<UserE> userEList = userDaoServiceImpl.findByOrgId(orgId);
            lable: for (UserE userE : userEList) {
                Set<RoleE> roleEList = userE.getRoles();
                for (RoleE roleE : roleEList) {
                    if (roleE.getId().equals(RoleEnum.ORG_MANAGER.getValue())) {
                        user = mapper.map(userE, User.class);
                        break lable;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("根据组织id查询组织管理员失败！");
        }
        return user;
    }

    @Override
    public void updateUserStatusByOrgId(String orgId) throws UserMgmtException {
        userDaoServiceImpl.updateUserStatusByOrgId(orgId, true);
    }

    @Override
    public List<User> findOrgUserByOrgId(String orgId) throws UserMgmtException {
        List<User> userList = new ArrayList<User>();
        try {
            Mapper mapper = new DozerBeanMapper();
            List<UserE> userEList = userDaoServiceImpl.findByOrgId(orgId);
            lable: for (UserE userE : userEList) {
                Set<RoleE> roleEList = userE.getRoles();
                for (RoleE roleE : roleEList) {
                    if (roleE.getId().equals(RoleEnum.ORG_USER.getValue())) {
                        userList.add(mapper.map(userE, User.class));
                        continue lable;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserMgmtException("根据组织id查询组织成员失败！");
        }
        return userList;
    }

//    @Override
//    public Map<String, Integer> countUserInfo(User user)
//            throws UserMgmtException {
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        Integer total = 0;
//        Integer onlineNum = 0;
//        try {
//            if (null != user) {
//                Mapper mapper = new DozerBeanMapper();
//                UserE userE = mapper.map(user, UserE.class);
//                List<UserE> userList = userDaoServiceImpl.findAllUsers(userE);
//                total = userList.size();
//                Collection<Session> sessions = sessionDAO.getActiveSessions();
//                for (UserE userFor : userList) {
//                    for (Session session : sessions) {
//                        PrincipalCollection p = (PrincipalCollection) session
//                                .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                        if (p != null) {
//                            if (userFor.getUsername().equals(
//                                    ((User) p.getPrimaryPrincipal())
//                                            .getUsername())) {
//                                onlineNum = onlineNum + 1;
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new UserMgmtException("查询用户详情失败！");
//        }
//        map.put("total", total);
//        map.put("onlineNum", onlineNum);
//        return map;
//    }

}
