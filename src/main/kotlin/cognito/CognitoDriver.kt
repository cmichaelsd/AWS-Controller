package cognito

import software.amazon.awssdk.regions.Region

object CognitoDriver {
    private val userPoolName = System.getenv("USER_POOL_NAME")
    private val region = Region.US_EAST_1

    private val create = Create(userPoolName, region)
    private val delete = Delete(userPoolName, region)

    fun run(operation: CognitoOperation) = when (operation) {
        CognitoOperation.CREATE -> create.run()
        CognitoOperation.DELETE -> delete.run()
    }
}