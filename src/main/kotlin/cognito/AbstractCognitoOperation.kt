package cognito

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException
import kotlin.system.exitProcess

abstract class AbstractCognitoOperation(private val region: Region) {
    fun run() {
        try {
            getCognitoIdentityProviderClient().use {
                operate(it)
            }
        } catch (e: CognitoIdentityProviderException) {
            println(e.message)
            exitProcess(1)
        }
    }

    abstract fun operate(cgClient: CognitoIdentityProviderClient)

    private fun getCognitoIdentityProviderClient(): CognitoIdentityProviderClient = CognitoIdentityProviderClient.builder()
        .region(region)
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build()
}