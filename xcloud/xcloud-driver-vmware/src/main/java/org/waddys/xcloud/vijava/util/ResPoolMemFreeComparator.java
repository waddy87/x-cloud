package org.waddys.xcloud.vijava.util;

import java.util.Comparator;

import com.vmware.vim25.mo.ResourcePool;

public class ResPoolMemFreeComparator implements Comparator<ResourcePool> {

    @Override
    public int compare(ResourcePool o1, ResourcePool o2) {
        long mem1 = o1.getRuntime().getMemory().unreservedForVm;
        long mem2 = o2.getRuntime().getMemory().unreservedForVm;
        if (mem1 - mem2 > 0) {
            return -1;
        } else if (mem1 - mem2 < 0) {
            return 1;
        }
        return 0;
    }

}
