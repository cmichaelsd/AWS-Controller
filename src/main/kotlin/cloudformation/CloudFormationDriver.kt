package cloudformation

import software.amazon.awssdk.regions.Region

object CloudFormationDriver {
    private val createTemplateURL = System.getenv("CREATE_TEMPLATE_URL")
    private val updateTemplateURL = System.getenv("UPDATE_TEMPLATE_URL")
    private val stackName = System.getenv("STACK_NAME")
    private val roleARN = System.getenv("ROLE_ARN")
    private val region = Region.US_EAST_1

    private val create = Create(createTemplateURL, stackName, roleARN, region)
    private val delete = Delete(stackName, roleARN, region)
    private val update = Update(updateTemplateURL, stackName, roleARN, region)
    private val detectDrift = DetectDrift(stackName, roleARN, region)

    fun run(operation: CloudFormationOperation) = when (operation) {
        CloudFormationOperation.CREATE       -> create.run()
        CloudFormationOperation.DELETE       -> delete.run()
        CloudFormationOperation.UPDATE       -> update.run()
        CloudFormationOperation.DETECT_DRIFT -> detectDrift.run()
    }
}