package com.rvkb.rfs

import woko.hbcompass.HibernateCompassStore
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.Buddy
import org.hibernate.criterion.Order
import com.rvkb.rfs.model.File
import org.hibernate.criterion.Restrictions

class RfsStore extends HibernateCompassStore {

    RfsStore(List<String> packageNames) {
        super(packageNames)
    }

    @Override
    Object load(String className, String key) {
        Class c = getMappedClass(className)
        if (c==File.class) {
            try {
                Long.parseLong(key)
            } catch(NumberFormatException) {
                // try to load by path !
                return loadFileByRelativePath(key)
            }
        }

        return super.load(className, key)    //To change body of overridden methods use File | Settings | File Templates.
    }

    File loadFileByRelativePath(String relativePath) {
        if (!relativePath.startsWith("/")) {
            relativePath = "/$relativePath"
        }
        session.createCriteria(File.class).add(Restrictions.eq("path", relativePath)).uniqueResult()
    }

    Config getConfig() {
        return (Config)session.get(Config.class, 1L)
    }

    void removeFile(String absolutePath) {
        session.delete("select f from com.rvkb.rfs.model.File as f where path = '$absolutePath'")
    }

    List<Buddy> getBuddies() {
        return session.createCriteria(Buddy.class).
          addOrder(Order.asc("name")).
          list()
    }

    List<File> getFiles() {
        return session.createCriteria(File.class).
            addOrder(Order.asc("path")).
            list()
    }

    void removeAllFiles() {
        session.delete("select f from com.rvkb.rfs.model.File as f")
    }
}
