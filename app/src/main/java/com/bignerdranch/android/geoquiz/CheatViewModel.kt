package com.bignerdranch.android.geoquiz

import android.os.Bundle
import androidx.lifecycle.ViewModel

private const val TAG = "CheatViewModel"

class CheatViewModel: ViewModel() {
    var cheaterStatus = false

    fun cheaterStatus() {
        cheaterStatus = true
    }


}