package org.scadalts.e2e.page.impl.criterias;

public interface GetXid {
    default Xid getXid() {
        return Xid.empty();
    }
}
