package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import com.puzzle.industries.data.storage.database.entity.reminder.ReminderWithExpenseEntity
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.services.ReminderService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class BootCompleteBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                if (intent.action == Intent.ACTION_BOOT_COMPLETED){
                    context.sendBroadcast(Intent(context, SetRemindersBroadcastReceiver::class.java))
                    context.sendBroadcast(Intent(context, SetAutoDeleteExpensesBroadcastReceiver::class.java))
                }
            }
        }

    }
}