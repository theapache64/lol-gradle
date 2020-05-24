package com.theapache64.lolgradle

import java.util.*

/**
 * Created by theapache64 : May 24 Sun,2020 @ 12:21
 */
open class LolGradlePluginExt {

    /**
     * To control logging
     */
    var isLoggingEnabled: Boolean = false

    /**
     * Directory name. If not given, project name will be considered as dirName.
     */
    var dirName: String? = null

    /**
     * Output directory path. If not given, home directory will be considered as outputDir
     */
    var outputDir: String? = null

    /**
     * To control what should happen if camera not available.
     */
    var lolPicStrategy: Strategy = Strategy.NONE
    var waitTimeInSec = 10L

    /**
     * Output style
     */
    var style: Style = Style.IMPACT

    enum class Style {
        IMPACT, MODERN
    }

    /**
     * Waiting strategy
     */
    enum class Strategy {
        /**
         * Do nothing
         */
        NONE,

        /**
         * Wait until #{waitTimeInSec}
         */
        WAIT,

        /**
         * Wait until camera available
         */
        WAIT_FOREVER,

        /**
         * Fail if camera not available
         */
        FAIL
    }
}