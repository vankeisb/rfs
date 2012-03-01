package com.rvkb.rfs

import woko.hbcompass.HibernateCompassStore
import com.rvkb.rfs.model.Config

class RfsStore extends HibernateCompassStore {

    RfsStore(List<String> packageNames) {
        super(packageNames)
    }

    Config getConfig() {
        return (Config)session.get(Config.class, 1L)
    }

    void removeFile(String absolutePath) {
        session.delete("select f from com.rvkb.rfs.model.File as f where path = '$absolutePath'")
    }
}
