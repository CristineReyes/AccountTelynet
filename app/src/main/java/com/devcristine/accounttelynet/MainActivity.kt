package com.devcristine.accounttelynet

import android.accounts.Account
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter : AccountAdapter? = null
    private var account:AccountModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        sqLiteHelper= SQLiteHelper(this)



        btnAgregar.setOnClickListener {  addAccount()}

        btnconsultarLista.setOnClickListener { getAccount() }

        btncEditAccount.setOnClickListener { updateAccount() }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.name, Toast.LENGTH_LONG).show()

            // update account
            edtaccountName.setText(it.name)
            edtaccountPhone.setText(it.phone)
            edtaccountEmail.setText(it.email)
            edtaccountVisited.setText(it.visited)

            account = it

        }


        // delete
        adapter?.setOnClickDeleteItem {
            deleteaccount(it.code)
        }

        // llamada
        adapter?.setOnClickCall {
            Toast.makeText(this,it.phone, Toast.LENGTH_LONG).show()
            var intent = Intent()
            intent.action = Intent.ACTION_DIAL // significa marcar
            //intent.data = Uri.parse("tel:${it.phone}")
            intent.data = Uri.parse ("tel:${it.phone}")
            startActivity(intent)

        }



    }


    private fun addDefaultCount(){

    }


    private fun addAccount(){
        val name = edtaccountName.text.toString()
        val phone=edtaccountPhone.text.toString()
        val email = edtaccountEmail.text.toString()
        val visited= edtaccountVisited.text.toString()




        if(name.isEmpty() || phone.isEmpty() || email.isEmpty() || visited.isEmpty()){
            Toast.makeText(this,"Faltan campos por llenar", Toast.LENGTH_LONG).show()
        }else{

            val account = AccountModel(name = name, phone = phone, email = email, visited = visited)
            val status = sqLiteHelper.insertAccounts(account)

            // verificar la insersion completa
            if(status > -1){
                Toast.makeText(this, "Cliente  Agregado...", Toast.LENGTH_SHORT).show()
                clearDataEdit()
                getAccount()
            }else{
                Toast.makeText(this, "Cliente  no fue Agregado...", Toast.LENGTH_SHORT).show()
                clearDataEdit()

            }
        }
    }

    private fun deleteaccount(code : Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Estas seguro de eliminar el cliente?")
        builder.setCancelable(true)
        builder.setPositiveButton("Si"){
            dialog, _ ->
            sqLiteHelper.deleteAccountById(code)
            getAccount()
            dialog.dismiss()
        }

        builder.setNegativeButton("No"){
            dialog, _ ->
        dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun callAccount(code: Int){

        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${account!!.phone}")

        //var intent = Intent()
       // intent.action = Intent.ACTION_DIAL // marcar significa marcar
        //intent.data = Uri.parse ("tel: 043184978981") // Este teléfono no se puede cambiar, los siguientes números se pueden cambiar a voluntad
       // intent.data = Uri.parse("tel:$phone")

        startActivity(intent)
    }




    private fun clearDataEdit() {
        edtaccountName.setText("")
        edtaccountPhone.setText("")
        edtaccountEmail.setText("")
        edtaccountVisited.setText("")
        edtaccountName.requestFocus()

    }

    private fun getAccount(){
        val accounList = sqLiteHelper.getAllAccount()
        Log.e(" app","${accounList.size}" )

        // RecyclerView
        adapter?.addItems(accounList)

    }


    private  fun getAccountVisit(){
        val accounListVisit = sqLiteHelper.getAllAccountVisit()
        Log.e(" app","${accounListVisit.size}" )

        // RecyclerView
        adapter?.addItems(accounListVisit)

    }

    private  fun getAccountNotVisit(){
        val accounListNotVisit = sqLiteHelper.getAllAccountNotVisit()
        Log.e(" app","${accounListNotVisit.size}" )

        // RecyclerView
        adapter?.addItems(accounListNotVisit)

    }

    private  fun getAccountName(){
        val accounListName = sqLiteHelper.getAllAccountNambe()
        Log.e(" app","${accounListName.size}" )

        // RecyclerView
        adapter?.addItems(accounListName)

    }

    private  fun getAccountCode(){
        val accounListCode = sqLiteHelper.getAllAccountCode()
        Log.e(" app","${accounListCode.size}" )

        // RecyclerView
        adapter?.addItems(accounListCode)

    }



    private fun updateAccount(){

        val name = edtaccountName.text.toString()
        val phone = edtaccountPhone.text.toString()
        val email = edtaccountEmail.text.toString()
        val visited = edtaccountVisited.text.toString()


        if(name.isEmpty() || phone.isEmpty() || email.isEmpty() || visited.isEmpty()){
            Toast.makeText(this,"No hay registros para Actualizar", Toast.LENGTH_LONG).show()
        }else {


            if (name == account?.name && phone == account?.phone && email == account?.email && visited == account?.visited) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()

                return

            }

            if (account == null)
                return
            val account = AccountModel(
                code = account!!.code,
                name = name,
                phone = phone,
                email = email,
                visited = visited
            )


            val status = sqLiteHelper.updateAccount(account)
            if (status > -1) {
                clearDataEdit()
                getAccount()

            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun initRecyclerView(){
       recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AccountAdapter()
        recyclerView.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.allAccount->{
                Toast.makeText(this, "Todos los Clientes", Toast.LENGTH_SHORT).show()
                getAccount()
            }

            R.id.orderVisit -> {
                Toast.makeText(this, "Visitados", Toast.LENGTH_SHORT).show()
                getAccountVisit()
            }

            R.id.ordernoVisit -> {
                Toast.makeText(this, "No Visitados", Toast.LENGTH_SHORT).show()
                getAccountNotVisit()

            }
            R.id.ordernombre -> {
                Toast.makeText(this, "Nombre", Toast.LENGTH_SHORT).show()
                getAccountName()
            }
            R.id.ordercodigo -> {
                Toast.makeText(this, "Codigo", Toast.LENGTH_SHORT).show()
                getAccountCode()
            }
        }

        return super.onOptionsItemSelected(item)
    }



}