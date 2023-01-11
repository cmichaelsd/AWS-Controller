package cognito

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.DescribeUserPoolRequest

class Create(
    private val userPoolName: String,
    region: Region
) : AbstractCognitoOperation(region) {
    override fun operate(cgClient: CognitoIdentityProviderClient) {
        val createCognitoRequest = CreateUserPoolRequest.builder()
            .poolName(userPoolName)
            .build()

        cgClient.createUserPool(createCognitoRequest)
    }
}