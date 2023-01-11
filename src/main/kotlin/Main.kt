import cloudformation.CloudFormationDriver
import cloudformation.CloudFormationOperation
import cognito.CognitoDriver
import cognito.CognitoOperation

fun main(args: Array<String>) {
    println("Application starts")

    val operations: List<AbstractOperation> = listOf(
        CloudFormationOperation.UPDATE
    )

    operations.forEach {
        when (it) {
            is CloudFormationOperation -> CloudFormationDriver.run(it)
            is CognitoOperation        -> CognitoDriver.run(it)
        }
    }

    println("Application ends")
}