package ktalex.dal.client

import ktalex.dal.ngrams.NgramsResponse

class NgramClient(openAlexBaseUrl: String? = null, mailTo: String? = null) : BaseClient(openAlexBaseUrl, mailTo) {

    override val entityType = "works"

    fun getByOpenAlexId(id: String): NgramsResponse = getEntity("$baseUrl/$id/ngrams")

    fun getByDoi(id: String): NgramsResponse = getEntity("$baseUrl/$id/ngrams")

    fun getNgrams(url: String): NgramsResponse = getEntity(url)

}
