package cloudformation

import software.amazon.awssdk.core.waiters.WaiterResponse
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudformation.CloudFormationClient
import software.amazon.awssdk.services.cloudformation.model.Capability
import software.amazon.awssdk.services.cloudformation.model.CreateStackRequest
import software.amazon.awssdk.services.cloudformation.model.DescribeStacksRequest
import software.amazon.awssdk.services.cloudformation.model.DescribeStacksResponse
import software.amazon.awssdk.services.cloudformation.waiters.CloudFormationWaiter

class Create(
    private val templateURL: String,
    stackName: String,
    roleARN: String,
    region: Region
) : AbstractCloudFormationOperation(stackName, roleARN, region) {
    override fun operate(cfClient: CloudFormationClient) {
        val createStackRequest: CreateStackRequest = CreateStackRequest
            .builder()
            .stackName(stackName)
            .templateURL(templateURL)
            .roleARN(roleARN)
            .capabilities(Capability.CAPABILITY_NAMED_IAM)
            .build()

        cfClient.createStack(createStackRequest)
    }

    override fun describeStack(cfClient: CloudFormationClient) {
        val waiter: CloudFormationWaiter = cfClient.waiter()

        val describeStacksRequest: DescribeStacksRequest = DescribeStacksRequest
            .builder()
            .stackName(stackName)
            .build()

        val waiterResponse: WaiterResponse<DescribeStacksResponse> = waiter
            .waitUntilStackCreateComplete(describeStacksRequest)

        waiterResponse.matched().response()?.let {
            println(it)
        }
    }
}