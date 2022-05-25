package com.lkorasik.doublehabits

import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import dagger.Component
import dagger.Module
import dagger.Provides
import java.time.Instant

@Component(modules = [ModuleA::class])
interface AppComponent {
    val habit: HabitEntity
}

@Module
object ModuleA {
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