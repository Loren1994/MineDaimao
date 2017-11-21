package com.example.loren.minesample.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Copyright © 21/11/2017 by loren
 */
@Entity
data class User(@Id var id: Long = 0, var name: String, var age: Int)