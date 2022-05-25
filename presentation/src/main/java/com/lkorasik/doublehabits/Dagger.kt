package com.lkorasik.doublehabits

import com.lkorasik.data.net.RequestContext
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.doublehabits.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import java.time.Instant

@Component(modules = [ModuleA::class])
interface AppComponent {
    val habit: HabitEntity

    val requests: RequestContext

    fun inject(activity: MainActivity)
}

@Module
object ModuleA {
    @Provides
    fun provideRequests(): RequestContext {
        return RequestContext
    }

    @Provides
    fun provideHabit(): HabitEntity {
        return HabitEntity(
            id = "a",
            title = "a",
            color = 0,
            count = 10,
            description = "Desc",
            frequency = "S",
            priority = HabitPriority.HIGH,
            type = HabitType.REGULAR,
            createdAt = Instant.now(),
            lastEditedAt = Instant.now()
        )
    }
}