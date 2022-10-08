package ru.android.spacextest.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.android.spacextest.R
import ru.android.spacextest.databinding.LauncherItemBinding
import ru.android.spacextest.models.LaunchesModel
import ru.android.spacextest.utils.RecyclerViewClickListener
import java.text.DateFormat
import java.text.SimpleDateFormat


class LaunchersAdapter(
    private val listenerClick: RecyclerViewClickListener
): RecyclerView.Adapter<LaunchersAdapter.ViewHolder>() {

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemViewType(position: Int): Int = position

    private val differCallback = object : DiffUtil.ItemCallback<LaunchesModel>() {
        override fun areItemsTheSame(oldItem: LaunchesModel, newItem: LaunchesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LaunchesModel, newItem: LaunchesModel): Boolean =
             oldItem == newItem
    }

    val itemViewModels = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchersAdapter.ViewHolder {
        val binding = LauncherItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchersAdapter.ViewHolder, position: Int) {
        holder.binding.clMainBlockLauncherItem.setOnClickListener {
            listenerClick.onRecyclerViewItemClick(
                holder.binding.clMainBlockLauncherItem,
                itemViewModels.currentList[position]
            )
        }
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemViewModels.currentList.size

    inner class ViewHolder(val binding: LauncherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(pos: Int) {
            val model = itemViewModels.currentList[pos]

            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateFormatOut: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormat.parse(model.date)

            binding.tvTitleMissionItem.text = model.name
            binding.tvCoresFlightMissionItem.text =
                itemView.context.getString(R.string.cores_flight_launcher_item) +
                        if (model.core[0].flight.isNullOrBlank())
                            "0"
                        else model.core[0].flight
            binding.tvSuccessStatusMissionItem.text =
                when (model.successStatus) {
                    true -> itemView.context.getString(R.string.status_success_launchers_item)
                    false -> itemView.context.getString(R.string.status_failure_launchers_item)
                    else -> itemView.context.getString(R.string.status_is_unknown_launchers_item)
                }
            binding.tvDateLaunceMissionItem.text =
                itemView.context.getString(R.string.date_launch_launcher_item) +
                        dateFormatOut.format(date!!)
            if (model.links.patch.smallLinkImage.isNullOrEmpty())
                binding.ivIconMissionItem.load(R.drawable.placeholder)
            else
                binding.ivIconMissionItem.load(model.links.patch.smallLinkImage) {
                    crossfade(true)
                    placeholder(R.color.grey_light_2)
                    error(R.drawable.placeholder)
                }
        }
    }
}

