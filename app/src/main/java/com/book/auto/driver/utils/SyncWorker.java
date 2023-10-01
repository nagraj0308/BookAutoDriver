package com.book.auto.driver.utils;


class SyncWorker() :Worker(ctx,params){

        override fun doWork():Result{
        val appContext=applicationContext

        makeStatusNotification("Blurring image",appContext)

        return try{
        val picture=BitmapFactory.decodeResource(
        appContext.resources,
        R.drawable.android_cupcake)

        val output=blurBitmap(picture,appContext)

        // Write bitmap to a temp file
        val outputUri=writeBitmapToFile(appContext,output)

        makeStatusNotification("Output is $outputUri",appContext)

        Result.success()
        }catch(throwable:Throwable){
        Log.e(TAG,"Error applying blur")
        Result.failure()
        }
        }
        }

