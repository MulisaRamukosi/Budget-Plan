package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class BootCompleteBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                if (intent.action == Intent.ACTION_BOOT_COMPLETED){
                    context.sendBroadcast(Intent(context, SetRemindersBroadcastReceiver::class.java))
                    context.sendBroadcast(Intent(context, SetAutoDeleteExpensesAlarmBroadcastReceiver::class.java))
                }
            }
        }

    }
}