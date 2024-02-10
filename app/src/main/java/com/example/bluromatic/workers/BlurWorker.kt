package com.example.bluromatic.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "BlurWorker"
class BlurWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    //implementacion del metodo doWork()
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )

        //sera para desenfocar la imagen
        return withContext(Dispatchers.IO) {
            //Proporciona un retraso entre los mensajes
            delay(DELAY_TIME_MILLIS)
            return@withContext try{
                val picture = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.android_cupcake
                )
                //esto servira para desenfocar la imagen
                val output = blurBitmap(picture, 1)

                val outputUri = writeBitmapToFile(applicationContext, output)
                //muestra un mensaje al usuaerio lo que contiene la variable outputUri
                makeStatusNotification(
                    "Output is $outputUri",
                    applicationContext
                )
                Result.success()
            } catch (throwable: Throwable) {
                //registra un mensaje de error
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )
                Result.failure()
            }
        }
    }
}

