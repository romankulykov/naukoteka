package medved.studio.domain.entities

class PasswordRequirementsEntity {
    var passwordRequirement: Int

    constructor(passwordRequirement: Int) {
        this.passwordRequirement = passwordRequirement
    }
}