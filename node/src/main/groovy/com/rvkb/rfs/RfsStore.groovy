package com.rvkb.rfs

import woko.hbcompass.HibernateCompassStore
import com.rvkb.rfs.model.Config

import org.hibernate.criterion.Order
import org.hibernate.criterion.Restrictions
import com.rvkb.rfs.model.User
import com.rvkb.rfs.model.FileTransfer

class RfsStore extends HibernateCompassStore {

    RfsStore(List<String> packageNames) {
        super(packageNames)
    }

    @Override
    Object load(String className, String key) {
        Class c = getMappedClass(className)
        if (c==com.rvkb.rfs.model.File.class) {
            try {
                Long.parseLong(key)
            } catch(NumberFormatException) {
                // try to load by path !
                return loadFileByRelativePath(key)
            }
        }

        return super.load(className, key)    //To change body of overridden methods use File | Settings | File Templates.
    }

    com.rvkb.rfs.model.File loadFileByRelativePath(String relativePath) {
        if (!relativePath.startsWith("/")) {
            relativePath = "/$relativePath"
        }
        session.createCriteria(com.rvkb.rfs.model.File.class).add(Restrictions.eq("path", relativePath)).uniqueResult()
    }

    Config getConfig() {
        return (Config)session.get(Config.class, 1L)
    }

    void removeFile(String absolutePath) {
        session.delete("select f from com.rvkb.rfs.model.File as f where path = '$absolutePath'")
    }

    List<User> getBuddies() {
        return session.createCriteria(User.class).
          addOrder(Order.asc("username")).
          add(Restrictions.eq("buddy", true)).
          list()
    }

    List<com.rvkb.rfs.model.File> getFiles() {
        return session.createCriteria(com.rvkb.rfs.model.File.class).
            addOrder(Order.desc("path")).
            list()
    }

    void removeAllFiles() {
        session.delete("select f from com.rvkb.rfs.model.File as f")
    }

    User getBuddy(String username) {
        session.createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult()
    }

    List<FileTransfer> getLastDownloads() {
        session.createCriteria(FileTransfer.class).setMaxResults(10).list()
    }

}
