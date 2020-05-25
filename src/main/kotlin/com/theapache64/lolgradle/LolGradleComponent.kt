package com.theapache64.lolgradle

import dagger.Component

/**
 * Created by theapache64 : May 25 Mon,2020 @ 11:41
 */
@Component
interface LolGradleComponent {
    fun inject(lolGradlePlugin: LolGradlePlugin)
}