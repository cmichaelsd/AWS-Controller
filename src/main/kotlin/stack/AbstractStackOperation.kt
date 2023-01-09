package stack

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudformation.CloudFormationClient
import software.amazon.awssdk.services.cloudformation.model.CloudFormationException
import kotlin.system.exitProcess

abstract class AbstractStackOperation(
    val stackName: String,
    val roleARN: String,
    private val region: Region
) {
    fun run() {
        try {
            getCloudFormationClient().let {
                operate(it)
                describeStack(it)
                it.close()
            }
        } catch (e: CloudFormationException) {
            println(e.message)
            exitProcess(1)
        }

        println("$stackName is ready")
    }

    abstract fun operate(cfClient: CloudFormationClient)

    abstract fun describeStack(cfClient: CloudFormationClient)

    private fun getCloudFormationClient(): CloudFormationClient = CloudFormationClient.builder()
        .region(region)
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build()
}