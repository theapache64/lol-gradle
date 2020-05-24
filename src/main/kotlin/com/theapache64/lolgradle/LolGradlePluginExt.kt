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
     * To control what should happen if camera not available.
     */
    var lolPicStrategy: Strategy = Strategy.NONE

    var waitTimeInSec = 10L

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