package org.waddys.xcloud.usermgmt.service.service;

import java.util.List;
import java.util.Map;

import org.waddys.xcloud.usermgmt.service.bo.User;
import org.waddys.xcloud.usermgmt.service.exception.UserMgmtException;

public interface UserService {
    public User save(User user) throws UserMgmtException;

    public void save(User user, String roleId) throws UserMgmtException;

    public List<User> findAllUsers() throws UserMgmtException;

    public User findUser(String id) throws UserMgmtException;

    public void delete(String id) throws UserMgmtException;

    public void update(User user) throws UserMgmtException;

    public Map<String, Object> findUsers(User user, Integer pageNum,
            Integer pageSize) throws UserMgmtException;

    public User findByUsername(String username) throws UserMgmtException;

    public void changePassword(String userId, String oldPassword,
            String newPassword) throws UserMgmtException;

    public void resetPassword(String userId, String newPassword)
            throws UserMgmtException;

    public User findOrgManagerByOrgId(String orgId) throws UserMgmtException;

    public List<User> findOrgUserByOrgId(String orgId) throws UserMgmtException;

    public void updateUserStatusByOrgId(String orgId) throws UserMgmtException;

//    public Map<String, Integer> countUserInfo(User user)
//            throws UserMgmtException;
}
