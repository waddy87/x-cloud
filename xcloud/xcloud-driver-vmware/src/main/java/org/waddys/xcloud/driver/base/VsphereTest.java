package org.waddys.xcloud.driver.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vim25.UserSession;
import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;

public class VsphereTest {
    private static Logger logger = LoggerFactory.getLogger(VsphereTest.class);

    public static void main(String[] args) throws Exception {
        ServiceInstance si = ConnectionPool.getConncetion("10.0.38.136", "administrator", "Zkrg;123");
        logger.debug("session size: " + si.getSessionManager().getSessionList().length);
        UserSession session = si.getSessionManager().getCurrentSession();
        logger.debug(">>>>>> session str: " + si.getServerConnection().getSessionStr());
        logger.debug("key: " + session.getKey());
        logger.info("uuid:{}, capability:{} ", si.getAboutInfo().getInstanceUuid());
        logger.info("about: " + si.getAboutInfo().getFullName());
        ConnectionPool.getStatus();
        logger.info("root folder: {}", si.getRootFolder().getName());
        ManagedEntity[] entitys = si.getRootFolder().getChildEntity();
        for (ManagedEntity entity : entitys) {
            logger.debug("name:{}, mor-type:{}", entity.getName(), entity.getMOR().getType());
            if (entity instanceof Datacenter) {
                Datacenter datacenter = (Datacenter) entity;
                //
                datacenter.getNetworks();
                datacenter.getDatastores();
                // 遍历主机文件夹
                Folder hostFolder = datacenter.getHostFolder();
                for (ManagedEntity hostSystem : hostFolder.getChildEntity()) {
                    logger.debug("name:{}, mor-type:{}", hostSystem.getName(), hostSystem.getMOR().getType());
                    if (hostSystem instanceof ClusterComputeResource) {
                        ClusterComputeResource cluster = (ClusterComputeResource) hostSystem;
                        logger.debug("res-pool:{}", cluster.getResourcePool().getName());
                        for (HostSystem host : cluster.getHosts()) {
                            logger.debug("  name:{}, mor-type:{}", host.getName(), host.getMOR().getType());
                        }
                    } else if (hostSystem instanceof ComputeResource) {
                        ComputeResource computeResource = (ComputeResource) hostSystem;
                        logger.debug("res-pool:{}", computeResource.getResourcePool().getName());
                        HostSystem host = computeResource.getHosts()[0];
                        logger.debug("  name:{}, mor-type:{}", host.getName(), host.getMOR().getType());
                    }
                }
            }
        }

    }

    void testConn() throws Exception {
        ServiceInstance si = ConnectionPool.getConncetion("10.0.38.136", "administrator", "Zkrg;123");
        UserSession session = si.getSessionManager().getCurrentSession();
        logger.debug(">>>>>> session str: " + si.getServerConnection().getSessionStr());
        logger.debug("uuid: {}, session size:{} ", si.getAboutInfo().getInstanceUuid(),
                si.getSessionManager().getSessionList().length);

    }

}