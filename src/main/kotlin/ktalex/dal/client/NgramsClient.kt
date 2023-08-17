package ktalex.dal.client

import ktalex.dal.ngrams.NgramsResponse
import ktalex.model.Ngram

class NgramsClient : BaseClient<Ngram>() {
    fun getNgrams(url: String): NgramsResponse = getEntity(url)!!

}