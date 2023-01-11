package cloudformation

import AbstractOperation

enum class CloudFormationOperation : AbstractOperation {
    CREATE,
    DELETE,
    UPDATE,
    DETECT_DRIFT
}