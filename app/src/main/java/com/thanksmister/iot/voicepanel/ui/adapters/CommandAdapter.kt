/*
 * Copyright (c) 2018 ThanksMister LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thanksmister.iot.voicepanel.ui.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanksmister.iot.voicepanel.R
import com.thanksmister.iot.voicepanel.persistence.Intent
import com.thanksmister.iot.voicepanel.utils.ComponentUtils
import com.thanksmister.iot.voicepanel.utils.DateUtils
import kotlinx.android.synthetic.main.adapter_commands.view.*
import timber.log.Timber

class CommandAdapter(private val items: List<Intent>?, private val listener: OnItemClickListener?) : RecyclerView.Adapter<CommandAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandAdapter.ViewHolder {
        Timber.d("onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_commands, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null) return 0
        return if (items.isNotEmpty()) items.size else 0
    }

    override fun onBindViewHolder(holder: CommandAdapter.ViewHolder, position: Int) {
        holder.bindItems(items!![position], position, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(item: Intent, position: Int, listener: OnItemClickListener?) {
            Timber.d("Bind Items")

            /*if(position == 0) {
                itemView.commandListItem.alpha = 0.9f
            } else if (position == 1 ) {
                itemView.commandListItem.alpha = 0.7f
            } else if (position == 2 ) {
                itemView.commandListItem.alpha = 0.6f
            } else if (position == 3 ) {
                itemView.commandListItem.alpha = 0.5f
            } else {
                itemView.commandListItem.alpha = 0.4f
            }*/

            itemView.commandListItem.alpha = 0.9f

            when {
                item.type == ComponentUtils.COMPONENT_WEATHER_FORECAST_TYPE ||
                item.type == ComponentUtils.COMPONENT_WEATHER_FORECAST_CONDITION_TYPE ||
                item.type == ComponentUtils.COMPONENT_WEATHER_FORECAST_ITEM_TYPE ||
                item.type == ComponentUtils.COMPONENT_WEATHER_FORECAST_TEMPERATURE_TYPE -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_cloudy)
                    itemView.commandTitle.text = "Weather"//itemView.context.getString(R.string.text_sensor_topic)
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_HA_ALARM_DISARM ||
                        item.type == ComponentUtils.COMPONENT_HA_ALARM_HOME ||
                        item.type == ComponentUtils.COMPONENT_HA_ALARM_STATUS ||
                        item.type == ComponentUtils.COMPONENT_HA_ALARM_DISARM_CODE ||
                        item.type == ComponentUtils.COMPONENT_HA_ALARM_AWAY  -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_lock)
                    itemView.commandTitle.text = "Alarm"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_HASS_OPEN_COVER -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_window_open)
                    itemView.commandTitle.text = "Open Cover"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_HASS_CLOSE_COVER  -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_window_closed)
                    itemView.commandTitle.text = "Close Cover"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_HASS_LIGHT_SET   -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_lightbulb)
                    itemView.commandTitle.text = "Light Set"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_HASS_TURN_ON   -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_light_switch)
                    itemView.commandTitle.text = "Turn On"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                // TODO add item name
                item.type == ComponentUtils.COMPONENT_HASS_TURN_OFF  -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_light_switch)
                    itemView.commandTitle.text = "Turn Off"
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                item.type == ComponentUtils.COMPONENT_SNIPS_INIT -> {
                    itemView.typeIcon.setImageResource(R.drawable.small_logo)
                    itemView.commandTitle.text = "Assistant Initialized"//itemView.context.getString(R.string.text_sensor_topic)
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
                else -> {
                    itemView.typeIcon.setImageResource(R.drawable.ic_hearing) // generic sensor icon
                    itemView.commandTitle.text = item.type
                    val date = DateUtils.parseLocaleDateTime(item.createdAt)
                    itemView.commandDate.text = date
                }
            }
            if (listener != null) {
                itemView.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }
}