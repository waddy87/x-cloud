package org.waddys.xcloud.intergration;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.waddys.xcloud.Application;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.project.po.dao.repository.ProjectRepository;
import org.waddys.xcloud.project.po.entity.ProjectE;
import org.waddys.xcloud.project.po.entity.ProjectVM;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.utils.MockUtils;
import org.waddys.xcloud.vm.po.dao.repository.VmHostRepository;
import org.waddys.xcloud.vm.po.dao.repository.VmNetRepository;
import org.waddys.xcloud.vm.po.entity.VmHostE;
import org.waddys.xcloud.vm.po.entity.VmNetE;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class VmRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private VmHostRepository repos;

    public void test2() {
//    	String ip="192.168.0.1";
//        String jpql = "select DISTINCT vm.id from vm_host vm LEFT JOIN vm_net net on vm.id=net.vm_id where 1=1 and ip='"+ip+"'";
//        em.createNativeQuery(jpql).executeUpdate();
//
//        String name = "admin";
//        jpql = "select count(u.username) from UserE u where username like :name order by createDate desc";
//        Query query = em.createQuery(jpql);
//        query.setParameter("name", name + "%");
//
//        // 设置查询结果的结束记录数
//        int maxResults = 3;
//        int pageNo = 1;
//        int pageSize = 3;
//        query.setMaxResults(maxResults);
//        // 设置查询结果的开始记录数（从0开始计数）
//        int firstResult = (pageNo - 1) * pageSize;
//        query.setFirstResult(firstResult);
//        List result = query.getResultList();
//        System.out.println(result.size());
    }

}
