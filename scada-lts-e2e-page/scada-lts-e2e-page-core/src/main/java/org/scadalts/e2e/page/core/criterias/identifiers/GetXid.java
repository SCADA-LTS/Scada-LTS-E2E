package org.scadalts.e2e.page.core.criterias.identifiers;

public interface GetXid {
    default Xid getXid() {
        return Xid.xidEmpty();
    }
}
