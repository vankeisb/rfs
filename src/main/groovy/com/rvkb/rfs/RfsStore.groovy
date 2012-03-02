package com.rvkb.rfs

import woko.hbcompass.HibernateCompassStore
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.Buddy
import org.hibernate.criterion.Order
import com.rvkb.rfs.model.File

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

    List<Buddy> getBuddies() {
        return session.createCriteria(Buddy.class).list()
    }

    List<File> getFiles() {
        return session.createCriteria(File.class).
            addOrder(Order.asc("path")).
            list()
    }
}
