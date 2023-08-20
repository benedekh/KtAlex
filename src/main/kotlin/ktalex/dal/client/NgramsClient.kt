package ktalex.dal.client

import ktalex.dal.ngrams.NgramsResponse
import ktalex.model.Ngram

class NgramsClient(mailTo: String? = null) : BaseClient<Ngram>(mailTo) {

    fun getNgrams(url: String): NgramsResponse = getEntity(url)!!

}