/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.scout48.auto.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scout48.auto.databinding.LiCarBinding
import com.scout48.auto.data.CarItem

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class PhotoListAdapter(private val onClickListener: OnClickListener ) :
        ListAdapter<CarItem,
                PhotoListAdapter.CarItemViewHolder>(DiffCallback) {

    /**
     * The CarPhotoViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [CarItem] information.
     */
    class CarItemViewHolder(private var binding: LiCarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(carItem: CarItem) {
            binding.car = carItem
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [CarItem]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<CarItem>() {
        override fun areItemsTheSame(oldItem: CarItem, newItem: CarItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CarItem, newItem: CarItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CarItemViewHolder {
        return CarItemViewHolder(LiCarBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CarItemViewHolder, position: Int) {
        val carItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(carItem)
        }
        holder.bind(carItem)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [CarItem]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [CarItem]
     */
    class OnClickListener(val clickListener: (CarItem: CarItem) -> Unit) {
        fun onClick(CarItem: CarItem) = clickListener(CarItem)
    }
}

