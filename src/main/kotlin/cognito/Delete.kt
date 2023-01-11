package cognito

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.DeleteUserPoolRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.DescribeUserPoolClientRequest


class Delete(
    private val userPoolId: String,
    region: Region
) : AbstractCognitoOperation(region) {
    override fun operate(cgClient: CognitoIdentityProviderClient) {
        val deleteCognitoRequest = DeleteUserPoolRequest.builder()
            .userPoolId(userPoolId)
            .build()

        cgClient.deleteUserPool(deleteCognitoRequest)
    }
}