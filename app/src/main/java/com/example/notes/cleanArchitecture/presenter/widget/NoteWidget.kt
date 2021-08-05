package com.example.notes.cleanArchitecture.presenter.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.annotation.LayoutRes
import com.example.notes.R
import com.example.notes.cleanArchitecture.domain.useCases.DeleteWidgetNotesByIDsUseCase
import com.example.notes.cleanArchitecture.domain.useCases.GetNotesByWidgetIdUseCase
import com.example.notes.cleanArchitecture.presenter.entities.PresenterWidgetNoteEntity
import com.example.notes.cleanArchitecture.presenter.entities.toPresentation
import com.example.notes.di.Injector
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class  NoteWidget: AppWidgetProvider() {
    companion object {
        @LayoutRes const val LAYOUT_RES = R.layout.note_widget

        fun setViewContent(id: Int, context: Context, notePresenter: PresenterWidgetNoteEntity) {
            val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
            RemoteViews(context.packageName, LAYOUT_RES).also { views ->
                with(notePresenter) {
                    views.setTextViewText(R.id.tv_header, header)
                    views.setTextViewText(R.id.tv_body, body)
                    views.setTextViewText(R.id.tv_title, desc)
                }

                appWidgetManager.updateAppWidget(id, views)
            }
        }
    }

    private val getNotesByWidgetIdUseCase: GetNotesByWidgetIdUseCase by lazy { Injector.component.getNotesByWidgetIdUseCase() }
    private val deleteWidgetUseCase: DeleteWidgetNotesByIDsUseCase by lazy { Injector.component.deleteWidgetUseCase() }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        getNotesByWidgetIdUseCase(appWidgetIds.map(Int::toLong))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { data ->
                data.forEach { (id, note) ->
                    setViewContent(id.toInt(), context, note.toPresentation())
                }
            }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        deleteWidgetUseCase(appWidgetIds.map(Int::toLong))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}