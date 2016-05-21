package org.waddys.xcloud.vijava.util;

import java.util.Comparator;

import com.vmware.vim25.mo.Datastore;

public class DatastoreSpaceComparator implements Comparator<Datastore> {

    @Override
    public int compare(Datastore o1, Datastore o2) {
        long freeSpace1 = o1.getSummary().getFreeSpace();
        long freeSpace2 = o2.getSummary().getFreeSpace();
        if (freeSpace1 - freeSpace2 > 0) {
            return -1;
        } else if (freeSpace1 - freeSpace2 < 0) {
            return 1;
        }
        return 0;

    }

}
