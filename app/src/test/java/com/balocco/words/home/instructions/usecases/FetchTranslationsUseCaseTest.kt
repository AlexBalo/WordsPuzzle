package com.balocco.words.home.instructions.usecases

import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.data.models.Translation
import com.balocco.words.data.parsers.TranslationParser
import com.balocco.words.data.remote.RemoteDataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val TRANSLATION_1 = "a"
private const val TRANSLATION_2 = "b"
private const val RESPONSE_STRING = TRANSLATION_1 + BREAK_LINE + TRANSLATION_2

class FetchTranslationsUseCaseTest {

    @Mock private lateinit var remoteDataSource: RemoteDataSource
    @Mock private lateinit var translationsStore: TranslationsStore
    @Mock private lateinit var translationParser: TranslationParser
    @Mock private lateinit var responseBody: ResponseBody
    @Mock private lateinit var translation: Translation
    @Mock private lateinit var throwable: Throwable

    private lateinit var useCase: FetchTranslationsUseCase

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = FetchTranslationsUseCase(
                remoteDataSource,
                translationsStore,
                translationParser)
    }

    @Test fun `When translations received, they are parsed and stored`() {
        whenever(remoteDataSource.getTranslations()).thenReturn(Observable.just(responseBody))
        whenever(responseBody.string()).thenReturn(RESPONSE_STRING)
        whenever(translationParser.parseTranslation(any())).thenReturn(translation)

        useCase.fetchTranslationFromRemoteSource()
                .test()
                .assertNoErrors()

        verify(translationParser).parseTranslation(TRANSLATION_1)
        verify(translationParser).parseTranslation(TRANSLATION_2)
        verify(translationsStore, times(2)).addTranslation(any())
    }

    @Test fun `When translations failed to be fetched, notify error`() {
        whenever(remoteDataSource.getTranslations()).thenReturn(Observable.error(throwable))

        useCase.fetchTranslationFromRemoteSource()
                .test()
                .assertError(throwable)
    }
}