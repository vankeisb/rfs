package com.rvkb.rfs.util

import net.sourceforge.stripes.action.StreamingResolution
import javax.servlet.http.HttpServletResponse

class DownloadResolution extends StreamingResolution {

    private Closure finish
    private Closure start
    private Closure error
    private File f

    DownloadResolution(File f) {
        super("rfsfile", new FileInputStream(f))
        setFilename(f.name)
        setRangeSupport(true)
    }

    @Override
    protected void stream(HttpServletResponse response) {
        if (start) {
            start()
        }
        try {
            super.stream(response)
        } catch(Exception e) {
            if (error) {
                error(e)
            }
        }
        if (finish) {
            finish()
        }
    }

    DownloadResolution onFinish(Closure c) {
        this.finish = c
        this
    }

    DownloadResolution onStart(Closure c) {
        this.start = c
        this
    }

    DownloadResolution onError(Closure c) {
        this.error = c
        this
    }

}
