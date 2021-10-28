package com.devcristine.accounttelynet

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Cristine R.M. on 27/10/2021.
 * DevCristineAguirre
 */
class AccountAdapter  : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private var accountList : ArrayList<AccountModel>  = ArrayList()
    private var onClickItem:((AccountModel)-> Unit)? = null
    private var onClickDeleteItem:((AccountModel)-> Unit)? = null
    private var onClickCall:((AccountModel)-> Unit)? = null

    fun addItems(items: ArrayList<AccountModel>){
        this.accountList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (AccountModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (AccountModel) -> Unit){
        this.onClickDeleteItem= callback
    }

    fun setOnClickCall(callback: (AccountModel) -> Unit){
        this.onClickCall= callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= AccountViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_account, parent, false)
    )


    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accountList[position]
        holder.bindView(account)

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(account)
        }

        holder.btnDelete.setOnClickListener {

                onClickDeleteItem?.invoke(account)
        }
        holder.btnCall.setOnClickListener {
          //  val intent = Intent(Intent.ACTION_CALL)
            //intent.data = Uri.parse("tel:${account.phone}")
            onClickCall?.invoke(account)
          //  account.phone


        }


    }

    override fun getItemCount(): Int {
        return accountList.size
    }


    class AccountViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var code= view.findViewById<TextView>(R.id.tvaccountCode)
        private var name= view.findViewById<TextView>(R.id.tvaccountName)
         var phone= view.findViewById<TextView>(R.id.tvaccountPhone)
        private var email= view.findViewById<TextView>(R.id.tvaccountEmail)
        private var visited= view.findViewById<TextView>(R.id.tvaccountVisited)
         var btnDelete = view.findViewById<Button>(R.id.buttonDelete)
         var btnCall = view.findViewById<Button>(R.id.btnCall)

        fun bindView(account: AccountModel){
            code.text= account.code.toString()
            name.text= account.name
            phone.text= account.phone
            email.text= account.email
            visited.text= account.visited

        }

    }
}