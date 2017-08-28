package com.balocco.words.home.instructions.usecases

import com.balocco.words.data.local.TranslationsStore
import com.balocco.words.data.parsers.TranslationParser
import com.balocco.words.data.remote.RemoteDataSource
import io.reactivex.Completable
import javax.inject.Inject

const val BREAK_LINE = "\n"

class FetchTranslationsUseCase @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val translationsStore: TranslationsStore,
        private val translationParser: TranslationParser
) {

    fun fetchTranslationFromRemoteSource(): Completable =
        remoteDataSource.getTranslations()
                .flatMapCompletable {
                    Completable.fromAction {
                        val responseBodyString = it.string()
                        val lines = responseBodyString.split(BREAK_LINE)
                        lines.map {
                            translationParser.parseTranslation(it)
                        }.forEach {
                            translationsStore.addTranslation(it)
                        }
                    }
                }
}