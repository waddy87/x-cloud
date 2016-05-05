package com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.serviceImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.entity.UserE;
import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.repository.UserRepository;
import com.sugon.cloudview.cloudmanager.usermgmt.serviceimpl.dao.service.UserDaoService;

@Component("userDaoServiceImpl")
@Transactional
public class UserDaoServiceImpl implements UserDaoService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserE save(UserE userE) {
        return userRepository.save(userE);
    }

    @Override
    public List<UserE> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserE findUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Override
    public void update(UserE userE) {
        userRepository.save(userE);
    }

    @Override
    public Page<UserE> findUsers(UserE user, Pageable pageable) {
        return userRepository.findAll(new Specification<UserE>() {
            @Override
            public Predicate toPredicate(Root<UserE> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate
                        .getExpressions();
                if (user != null) {
                    if (StringUtils.isNotEmpty(user.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"),
                                user.getOrgId()));
                    }
                    if (StringUtils.isNotEmpty(user.getUsername())) {
                        expressions.add(cb.like(root.get("username"), "%"
                                + user.getUsername() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
    }

    @Override
    public long count(UserE userE) {
        return userRepository.countByUsername(userE.getUsername());
    }

    @Override
    public UserE findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserE> findByOrgId(String orgId) {
        return userRepository.findByOrgId(orgId);
    }

    @Override
    public void updateUserStatusByOrgId(String orgId, Boolean isDelete) {
        userRepository.updateUserStatusByOrgId(orgId, isDelete);

    }

    @Override
    public List<UserE> findAllUsers(UserE userE) {
        return userRepository.findAll(new Specification<UserE>() {
            @Override
            public Predicate toPredicate(Root<UserE> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate
                        .getExpressions();
                if (userE != null) {
                    if (StringUtils.isNotEmpty(userE.getOrgId())) {
                        expressions.add(cb.equal(root.get("orgId"),
                                userE.getOrgId()));
                    }
                }
                return predicate;
            }
        });
    }

}
