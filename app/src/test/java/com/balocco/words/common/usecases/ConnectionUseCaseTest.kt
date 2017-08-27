package com.balocco.words.common.usecases

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ConnectionUseCaseTest {

    @Mock private lateinit var context: Context
    @Mock private lateinit var manager: ConnectivityManager
    @Mock private lateinit var networkInfo: NetworkInfo

    private lateinit var useCase: ConnectionUseCase

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = ConnectionUseCase(context)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager)
    }

    @Test fun `When is device connected and no network info, returns false`() {
        val isConnected = useCase.isDeviceConnected()

        assertThat(isConnected).isFalse()
    }

    @Test fun `When is device connected and network info not connected, returns false`() {
        whenever(manager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isConnectedOrConnecting).thenReturn(false)

        val isConnected = useCase.isDeviceConnected()

        assertThat(isConnected).isFalse()
    }

    @Test fun `When is device connected and network info connected, returns true`() {
        whenever(manager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isConnectedOrConnecting).thenReturn(true)

        val isConnected = useCase.isDeviceConnected()

        assertThat(isConnected).isTrue()
    }
}