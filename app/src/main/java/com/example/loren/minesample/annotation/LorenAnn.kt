package com.example.loren.minesample.annotation

import java.lang.annotation.Inherited

/**
 * Copyright © 22/11/2017 by loren
 */

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Inherited
@Retention(AnnotationRetention.RUNTIME)
annotation class LorenAnn(val name: String = "", val age: Int = 0)
