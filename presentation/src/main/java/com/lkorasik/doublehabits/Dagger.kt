package com.lkorasik.doublehabits

import com.lkorasik.data.Deps
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.doublehabits.ui.MainActivity
import com.lkorasik.doublehabits.ui.fragments.HabitEditorFragment
import com.lkorasik.doublehabits.ui.fragments.HabitListBaseFragment
import com.lkorasik.doublehabits.ui.fragments.HabitsListFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import java.time.Instant

@Component(modules = [ModuleA::class, Deps::class])
interface AppComponent {
    val habit: HabitEntity

    fun inject(activity: MainActivity)
    fun inject(fragment: HabitsListFragment)
    fun inject(fragment: HabitListBaseFragment)
    fun inject(fragment: HabitEditorFragment)
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
            lastEditedAt = Instant.now(),
            done_dates = listOf()
        )
    }
}