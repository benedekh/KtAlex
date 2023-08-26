package ktalex.dal.client

import ktalex.dal.ngrams.NgramsResponse
import ktalex.model.Work

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class NgramClient(openAlexBaseUrl: String? = null, mailTo: String? = null) : BaseClient(openAlexBaseUrl, mailTo) {

    override val entityType = "works"

    /**
     * Get Ngrams by the OpenAlex ID of the corresponding [Work].
     *
     * @param id the OpenAlex ID of the [Work]
     */
    fun getByOpenAlexId(id: String): NgramsResponse = getEntity("$baseUrl/$id/ngrams")

    /**
     * Get Ngrams by the DOI of the corresponding [Work].
     *
     * @param id the DOI of the [Work]
     */
    fun getByDoi(id: String): NgramsResponse = getEntity("$baseUrl/$id/ngrams")

    /**
     * Get ngrams by their URL.
     *
     * @param id the URL of the ngrams
     */
    fun getNgrams(url: String): NgramsResponse = getEntity(url)

}
