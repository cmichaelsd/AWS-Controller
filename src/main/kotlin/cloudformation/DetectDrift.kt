package cloudformation

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudformation.CloudFormationClient
import software.amazon.awssdk.services.cloudformation.model.DescribeStackDriftDetectionStatusRequest
import software.amazon.awssdk.services.cloudformation.model.DescribeStackResourceDriftsRequest
import software.amazon.awssdk.services.cloudformation.model.DetectStackDriftRequest

class DetectDrift(
    stackName: String,
    roleARN: String,
    region: Region
) : AbstractCloudFormationOperation(stackName, roleARN, region) {
    var stackDriftDetectionId: String? = null

    override fun operate(cfClient: CloudFormationClient) {
        val detectStackDriftRequest: DetectStackDriftRequest = DetectStackDriftRequest
            .builder()
            .stackName(stackName)
            .build()

        stackDriftDetectionId = cfClient.detectStackDrift(detectStackDriftRequest).stackDriftDetectionId()
    }

    override fun describeStack(cfClient: CloudFormationClient) {
        stackDriftDetectionId ?: return

        val describeStacksRequest: DescribeStackDriftDetectionStatusRequest = DescribeStackDriftDetectionStatusRequest
            .builder()
            .stackDriftDetectionId(stackDriftDetectionId)
            .build()

        cfClient.describeStackDriftDetectionStatus(describeStacksRequest)

        val describeStackResourceDriftsRequest: DescribeStackResourceDriftsRequest = DescribeStackResourceDriftsRequest
            .builder()
            .stackName(stackName)
            .build()

        cfClient.describeStackResourceDrifts(describeStackResourceDriftsRequest).stackResourceDrifts().first().apply {
            println(stackResourceDriftStatus())
        }
    }
}