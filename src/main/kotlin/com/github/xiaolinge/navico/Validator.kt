package com.github.xiaolinge.navico

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Validation
import javax.validation.ValidatorFactory

object Validator {
    val validatorFactory: ValidatorFactory = Validation.byDefaultProvider()
        .configure()
        .messageInterpolator(ParameterMessageInterpolator())
        .buildValidatorFactory()
}

fun View.isInvalid(): Boolean = Validator.validatorFactory.validator.validate(this).isNotEmpty()

fun Chart.isInvalid(): Boolean = Validator.validatorFactory.validator.validate(this).isNotEmpty()

fun Colour.isInvalid(): Boolean = Validator.validatorFactory.validator.validate(this).isNotEmpty()
