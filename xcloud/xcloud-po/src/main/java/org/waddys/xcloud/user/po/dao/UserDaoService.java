package org.waddys.xcloud.user.po.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.waddys.xcloud.user.po.entity.UserE;

public interface UserDaoService {
    public UserE save(UserE userE);

    public List<UserE> findAllUsers();

    public UserE findUser(String id);

    public void delete(String id);

    public void update(UserE userE);

    public Page<UserE> findUsers(UserE user, Pageable pageable);

    public long count(UserE userE);

    public UserE findByUsername(String username);

    public List<UserE> findByOrgId(String orgId);

    public void updateUserStatusByOrgId(String orgId, Boolean isDelete);

    public List<UserE> findAllUsers(UserE userE);

	public Page<UserE> findByRole(String roleId, String roleName, Pageable pageable);
}
