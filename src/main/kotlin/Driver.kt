import software.amazon.awssdk.regions.Region
import stack.*

class Driver {
    companion object {
        private val createTemplateURL = System.getenv("CREATE_TEMPLATE_URL")
        private val updateTemplateURL = System.getenv("UPDATE_TEMPLATE_URL")
        private val stackName = System.getenv("STACK_NAME")
        private val roleARN = System.getenv("ROLE_ARN")
        private val region = Region.US_EAST_1

        fun run(operations: List<Operation>) = operations.forEach {
            load(it)
        }

        private fun load(operation: Operation) = when (operation) {
            Operation.CREATE       -> Create(createTemplateURL, stackName, roleARN, region).run()
            Operation.DELETE       -> Delete(stackName, roleARN, region).run()
            Operation.UPDATE       -> Update(updateTemplateURL, stackName, roleARN, region).run()
            Operation.DETECT_DRIFT -> DetectDrift(stackName, roleARN, region).run()
        }
    }
}