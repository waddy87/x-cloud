package com.sugon.cloudview.cloudmanager.resource.service.service.venv;

import java.util.List;

import com.sugon.cloudview.cloudmanager.resource.service.bo.venv.Cluster;
import com.sugon.cloudview.cloudmanager.resource.service.exception.venv.VenvException;

/**
 * 功能名称：集群接口 20150307
 * 
 * @author ghj
 * @version Cloudview sp2.0
 */
public interface ClusterService {

    public Cluster addCluster(Cluster cluster, String venvConfigId) throws VenvException;

    public Cluster findByclusterId(String clusterId) throws VenvException;

    public Iterable<Cluster> findAll() throws VenvException;

    public List<Cluster> findByName(String name);
}
