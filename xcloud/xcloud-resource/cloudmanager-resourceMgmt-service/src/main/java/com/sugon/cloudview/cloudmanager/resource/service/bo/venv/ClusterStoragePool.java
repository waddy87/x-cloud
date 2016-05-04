package com.sugon.cloudview.cloudmanager.resource.service.bo.venv;

/**
 * 集群与存储池
 * @author ghj
 */
public class ClusterStoragePool {
    private String clusterStoragePoolId;

    /**
     * 存储池Id
     */
    private CloudvmStoragePool cloudvm_StoragePool;

    public CloudvmStoragePool getCloudvm_StoragePool() {
        return cloudvm_StoragePool;
    }

    public void setCloudvm_StoragePool(CloudvmStoragePool cloudvm_StoragePool) {
        this.cloudvm_StoragePool = cloudvm_StoragePool;
    }

    /**
     * 集群id（底层cloudVM中集群 id）
     */
    private Cluster cluster;

    public String getClusterStoragePoolId() {
        return clusterStoragePoolId;
    }

    public void setClusterStoragePoolId(String clusterStoragePoolId) {
        this.clusterStoragePoolId = clusterStoragePoolId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
