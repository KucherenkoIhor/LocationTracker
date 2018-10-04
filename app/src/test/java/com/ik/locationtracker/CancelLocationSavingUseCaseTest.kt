package com.ik.locationtracker

import android.app.AlarmManager
import android.app.PendingIntent
import com.ik.locationtracker.domains.services.JobScheduler
import com.ik.locationtracker.domains.usecases.CancelLocationSavingUseCase
import com.ik.locationtracker.domains.usecases.CancelLocationSavingUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

/**
 * Created by ihor_kucherenko on 10/4/18.
 * https://github.com/KucherenkoIhor
 */
class CancelLocationSavingUseCaseTest: Spek({
        val alarmManager = mockk<AlarmManager>()
        val pendingIntent = mockk<PendingIntent>()
        val jobScheduler = mockk<JobScheduler> {
            every { cancelScheduledJob(alarmManager, pendingIntent) } returns Unit
        }
        val useCase: CancelLocationSavingUseCase by memoized { CancelLocationSavingUseCaseImpl(alarmManager, jobScheduler, pendingIntent) }

    describe("CancelLocationSavingUseCase using") {
        on("the cancel() methos is invoked") {
            useCase.cancel()
            it("invokes the cancelScheduledJob method of JobScheduler") {
                verify { jobScheduler.cancelScheduledJob(alarmManager, pendingIntent) }
            }
        }
    }
})