package com.ik.locationtracker

import com.ik.locationtracker.layers.services.JobScheduler
import com.ik.locationtracker.layers.usecases.CancelLocationSavingUseCase
import com.ik.locationtracker.layers.usecases.CancelLocationSavingUseCaseImpl
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
class CancelLocationSavingUseCaseTest : Spek({

    val jobScheduler = mockk<JobScheduler> {
        every { cancelScheduledJob() } returns Unit
    }
    val useCase: CancelLocationSavingUseCase by memoized { CancelLocationSavingUseCaseImpl(jobScheduler) }

    describe("CancelLocationSavingUseCase using") {
        on("the cancel() method is invoked") {
            useCase()
            it("invokes the cancelScheduledJob method of JobScheduler") {
                verify { jobScheduler.cancelScheduledJob() }
            }
        }
    }
})