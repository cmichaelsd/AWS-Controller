package cloudformation

import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudformation.CloudFormationClient
import software.amazon.awssdk.services.cloudformation.model.*
import software.amazon.awssdk.services.cloudformation.waiters.CloudFormationWaiter

class Delete(
    stackName: String,
    roleARN: String,
    region: Region
) : AbstractCloudFormationOperation(stackName, roleARN, region) {
    override fun operate(cfClient: CloudFormationClient) {
        val createStackRequest: DeleteStackRequest = DeleteStackRequest
            .builder()
            .stackName(stackName)
            .roleARN(roleARN)
            .build()

        cfClient.deleteStack(createStackRequest)
    }

    override fun describeStack(cfClient: CloudFormationClient) {
        val waiter: CloudFormationWaiter = cfClient.waiter()

        val describeStacksRequest: DescribeStacksRequest = DescribeStacksRequest
            .builder()
            .stackName(stackName)
            .build()

        val waiterResponse: WaiterResponse<DescribeStacksResponse> = waiter
            .waitUntilStackDeleteComplete(describeStacksRequest)

        waiterResponse.matched().response()?.let {
            println(it)
        }
    }
}