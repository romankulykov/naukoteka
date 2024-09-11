package uddug.com.naukoteka.global.base

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.shuhart.stickyheader.StickyAdapter
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.SectionType
import uddug.com.naukoteka.R
import java.util.ArrayList

abstract class BaseStickyAdapter<T : Section, VH : BaseViewHolder<T>> :
    StickyAdapter<VH, VH> {

    private var items: MutableList<T>? = ArrayList()
    internal var onItemClickListener: ((T, position: Int, view: View) -> Unit?)? = null

    open val listItemHeader: Int = R.layout.item_view_header

    abstract val listItemView: Int

    protected abstract fun newItemViewHolder(parent: ViewGroup, viewType: Int): VH
    protected abstract fun newItemHeaderViewHolder(parent: ViewGroup, viewType: Int): VH

    constructor()

    constructor(items: List<T>) {
        this.items = items.toMutableList()
    }

    fun getItem(position: Int): T? {
        return if (position < 0 || position >= itemCount) null else items?.get(position)
    }

    override fun getItemViewType(position: Int): Int {
        return items?.get(position)?.type()?.type ?: -1
    }

    fun getItems(): List<T>? {
        return items?.toList()
    }

    open fun setItems(items: List<T>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    open fun removeItem(position: Int) {
        items?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItems(items: List<T>) {
        if (this.items == null)
            setItems(items)
        else {
            this.items?.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun addItem(item: T) {
        if (this.items == null)
            this.items = ArrayList()
        items?.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun clear() {
        if (items != null) {
            items?.clear()
            notifyDataSetChanged()
        }
    }

    fun updateItem(position: Int, newItem: T): Boolean {
        if (position < 0 || position >= itemCount) return false
        items?.set(position, newItem)
        notifyItemChanged(position)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val holder = if (viewType == SectionType.Header.type) {
            newItemHeaderViewHolder(parent, viewType)
        } else {
            newItemViewHolder(parent, viewType)
        }
        onItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener { view ->
                val position = holder.adapterPosition
                items?.let {
                    if (checkCorrectPosition(position))
                        getItem(position)?.let { listener.invoke(it, position, view) }
                }

            }
        }
        return holder
    }

    fun checkCorrectPosition(position: Int): Boolean =
        (0 <= position && position < items?.size ?: 0)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        val item = getItems()?.get(position)
        item?.let { holder.updateView(it) }
    }


    override fun getItemCount(): Int {
        items?.run {
            return size
        }
        return 0
    }


    fun setOnItemClickListener(onItemClickListener: (T, position: Int, view: View) -> Unit?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val type = items!![position].type()
        val section = items!![position].sectionPosition
        if (type == SectionType.Header) {
            getItem(position)?.let { holder.updateView(it) }
        }
    }

    class HeaderViewHolder constructor(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<Section>(layoutRes, parent) {
        var textView: TextView = itemView.findViewById(R.id.tv_header)

        override fun updateView(item: Section) {
            textView.text = item.sectionName()
        }

    }

}