package ktalex.dal.works

import ktalex.dal.BaseClient
import ktalex.model.Work

class WorksClient : BaseClient() {
    companion object {
        const val WORKS_URL = "${BASE_URL}/works"
    }

    suspend fun getByOpenAlexId(openAlexId: String): Work? = getItem("$WORKS_URL/$openAlexId")

    suspend fun getByDoi(doi: String): Work? = getItem("$WORKS_URL/$doi")

    suspend fun getByMicrosoftAcademicGraphId(magId: String): Work? = getItem("$WORKS_URL/mag:$magId")

    suspend fun getByPubMedId(pmId: String): Work? = getItem("$WORKS_URL/pmid:$pmId")

}