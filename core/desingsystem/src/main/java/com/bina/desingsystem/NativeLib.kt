package com.bina.desingsystem

class NativeLib {

    /**
     * A native method that is implemented by the 'desingsystem' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'desingsystem' library on application startup.
        init {
            System.loadLibrary("desingsystem")
        }
    }
}